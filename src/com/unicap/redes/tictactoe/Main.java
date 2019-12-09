package com.unicap.redes.tictactoe;

import com.unicap.redes.tictactoe.server.UDPConnection;

public class Main {

    public static void main(String[] args) {
        UDPConnection connection = new UDPConnection();
        connection.runServer();

    }
}
