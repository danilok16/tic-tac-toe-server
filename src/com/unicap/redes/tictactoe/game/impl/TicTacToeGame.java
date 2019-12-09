package com.unicap.redes.tictactoe.game.impl;

import com.unicap.redes.tictactoe.entities.Client;
import com.unicap.redes.tictactoe.game.Board;
import com.unicap.redes.tictactoe.game.Game;

public class TicTacToeGame implements Game {
    private int id;
    private int turn;
    private int startedBy;
    private Board board;
    private Client player1;
    private Client player2;
    private int round;
    private boolean finished;
    private int winner;

    public TicTacToeGame(Board board){
        this.board = board;
        this.turn = 0;
        this.round = 0;
        this.startedBy = 1;
    }

    @Override
    public int makeMove(int piece, int position){
        if(piece!= this.turn || this.finished)
            return this.winner;
        this.board.setMove(piece,position);
        this.winner = this.checkWinner();
        if(this.winner == 0 && this.round > 8){
            this.finished = true;
            return -1; //tie
        }
        this.round++;
        this.turn = this.turn == 1 ? 2 : 1;
        return winner;
    }
    @Override
    public void initBoard() {

    }

    @Override
    public Board getBoard() {
        return this.board;
    }
    @Override
    public String newPlayer(Client player){
        if(this.player1 == null){
            this.player1 = player;
            this.player1.setId(1);
            return "1";
        }
        else if(this.player2 == null){
            this.player2 = player;
            this.turn=1;
            this.player2.setId(2);
            return "2";
        }
        return "-1";
    }
    @Override
    public void logout(Client player){
        if(player1.getId() == player.getId()){
            this.player1 = null;
        }
        else if(player2.getId() == player.getId()){
            this.player2 = null;
        }
        this.board.resetBoard();
    }
    @Override
    public void restart(){
        this.round = 0;
        this.startedBy = this.startedBy == 1 ? 2:1;
        this.turn = startedBy;
        this.finished = false;
        this.board.resetBoard();
        this.winner = 0;
    }



    private int checkWinner() {
        for (int a = 0; a < 8; a++) {
            String winner = "";
            switch (a) {
                case 0:
                    winner = board.getBoard()[0] + Integer.toString(board.getBoard()[1]) + board.getBoard()[2];
                    break;
                case 1:
                    winner = board.getBoard()[3] + Integer.toString(board.getBoard()[4]) + board.getBoard()[5];
                    break;
                case 2:
                    winner = board.getBoard()[6] + Integer.toString(board.getBoard()[7]) + board.getBoard()[8];
                    break;
                case 3:
                    winner = board.getBoard()[0] + Integer.toString(board.getBoard()[3]) + board.getBoard()[6];
                    break;
                case 4:
                    winner = board.getBoard()[1] + Integer.toString(board.getBoard()[4]) + board.getBoard()[7];
                    break;
                case 5:
                    winner = board.getBoard()[2] + Integer.toString(board.getBoard()[5]) + board.getBoard()[8];
                    break;
                case 6:
                    winner = board.getBoard()[0] + Integer.toString(board.getBoard()[4]) + board.getBoard()[8];
                    break;
                case 7:
                    winner = board.getBoard()[2] + Integer.toString(board.getBoard()[4]) + board.getBoard()[6];
                    break;
            }
            if (winner.equals("111")) {
                this.finished = true;
                return 1;
            } else if (winner.equals("222")) {
                this.finished = true;
                return 2;
            }
        }
        return 0;
    }
    @Override
    public int turn(){
        return this.turn;
    }
    @Override
    public boolean isFinished(){
        return this.finished;
    }
    @Override
    public int getWinner(){
        return this.winner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getStartedBy() {
        return startedBy;
    }

    public void setStartedBy(int startedBy) {
        this.startedBy = startedBy;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Client getPlayer1() {
        return player1;
    }

    public void setPlayer1(Client player1) {
        this.player1 = player1;
    }

    public Client getPlayer2() {
        return player2;
    }

    public void setPlayer2(Client player2) {
        this.player2 = player2;
    }
}
