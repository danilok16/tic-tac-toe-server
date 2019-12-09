package com.unicap.redes.tictactoe.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.unicap.redes.tictactoe.common.CommunicationCode;
import com.unicap.redes.tictactoe.common.TransferObject;
import com.unicap.redes.tictactoe.entities.Client;
import com.unicap.redes.tictactoe.game.Game;
import com.unicap.redes.tictactoe.game.impl.TicTacToeBoard;
import com.unicap.redes.tictactoe.game.impl.TicTacToeGame;

public class UDPConnection {
    private final int bufferLength = 1024;
    private DatagramSocket socket;
    private boolean severRunning;
    private byte [] buffer;
    private final int port = 8085;
    private List<Client> ClientsConnected;
    private InetAddress serverIp;

    //private Board board;
    private Game game;

    public UDPConnection() {
        try {
            this.game = new TicTacToeGame(new TicTacToeBoard());
            socket = new DatagramSocket(port);
            serverIp = InetAddress.getByName("localhost");
            this.ClientsConnected = new ArrayList<>();
            buffer = new byte[bufferLength];
        } catch (SocketException | UnknownHostException e) {
            System.out.println("SERVER: ERROR OPENING SOCKET AT PORT: " + port +
                                 "\nERROR MESSAGE: " + e.getMessage());
        }
    }

    public void runServer(){
        this.severRunning = true;

        System.out.println("SERVER: SERVER RUNNING AT "+ this.socket.getLocalSocketAddress()+ ":" + port);
        try {
            while (this.severRunning){
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                this.socket.receive(packet);
//                this.analyzeRequest(packet);

                socket.send(this.analyzeRequest(packet));
            }
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("SERVER: ERROR "+ e.getMessage());
        }
        finally {
            socket.close();
            System.out.println("SEVER: SEVER STOPPED!");
        }
    }

    private TransferObject datagramToTransferObject(DatagramPacket packet) throws IOException, ClassNotFoundException {
        byte [] data = packet.getData();
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);
        TransferObject receivedObject = (TransferObject) objectInputStream.readObject();
        System.out.println("Obj recived: " + receivedObject);
        return  receivedObject;
    }
    private DatagramPacket analyzeRequest(DatagramPacket packet) throws IOException, ClassNotFoundException {
        int opCode;
        TransferObject receivedObject = this.datagramToTransferObject(packet);
        opCode = receivedObject.getCode();
        DatagramPacket response = null;
        buffer = new byte [bufferLength];

        System.out.println(
                "CLIENT REQUEST: " +
                "OP_CODE: [" + opCode + "]"+
                " ADDRESS: " + packet.getAddress() + ":" + packet.getPort()
        );

        if(opCode >= CommunicationCode.values().length || opCode < 0){
            opCode = CommunicationCode.INVALID.ordinal();
        }


        switch (CommunicationCode.values()[opCode]){
            case LOGIN:
                response = this.buildResponse(CommunicationCode.LOGIN.ordinal(),this.login(packet) ,packet.getAddress(), packet.getPort());
                break;
            case GET_BOARD:
                System.out.println("Board was requested!");
                if(this.game.isFinished()){
                    response = this.buildResponse(CommunicationCode.END_GAME.ordinal(), Integer.toString(this.game.getWinner()),packet.getAddress(), packet.getPort());
                }
                else {
                    response = this.buildResponse(CommunicationCode.OK.ordinal(), Arrays.toString(this.game.getBoard().getBoard()),packet.getAddress(), packet.getPort());
                }
                break;
            case MAKE_A_MOVE:
                System.out.println("Moved!");
                int moveResult = this.makeMove(receivedObject.getMessage());
                if(moveResult != 0){
                    response = this.buildResponse(CommunicationCode.END_GAME.ordinal(), Integer.toString(moveResult),packet.getAddress(), packet.getPort());
                }else {
                    response = this.buildResponse(CommunicationCode.OK.ordinal(), "", packet.getAddress(), packet.getPort());
                }
                break;
            case RESTART:
                this.resetBoard();
                response = this.buildResponse(CommunicationCode.OK.ordinal(),"",packet.getAddress(), packet.getPort());
                break;
            case LOGOUT:
                this.logout(packet);
                response = this.buildResponse(CommunicationCode.OK.ordinal(), "", packet.getAddress(), packet.getPort());
                break;
            case CLIENT_QUEUE:

                break;
            case INVALID:
                // TODO: TAKE IT OUT!
                System.out.println("SERVER: OPCODE INVALID!");

        }
        return response;
    }
    private DatagramPacket buildResponse(int code, String message, InetAddress address, int port) throws IOException {
        TransferObject objectToSend = new TransferObject(code, message, this.game.turn());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(objectToSend);
        byte[] data = outputStream.toByteArray();
        return new DatagramPacket(data, data.length, address, port);

    }
    private int makeMove(String message){
        int moveResult;
        String [] move = message.split(",");
        int piece = Integer.parseInt(move[0]);
        int position = Integer.parseInt(move[1]);
        return this.game.makeMove(piece,position);

    }
    private String login(DatagramPacket packet){
        return this.game.newPlayer(new Client(-1,"", packet.getAddress(), packet.getPort()));
    }
    private void resetBoard(){
        this.game.restart();
    }
    private void logout(DatagramPacket packet) throws IOException, ClassNotFoundException {
        int cod = Integer.parseInt(datagramToTransferObject(packet).getMessage());
        this.game.logout(new Client(cod,"", packet.getAddress(), packet.getPort()));
    }
}
