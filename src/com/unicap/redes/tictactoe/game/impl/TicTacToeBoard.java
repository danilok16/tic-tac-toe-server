package com.unicap.redes.tictactoe.game.impl;

import com.unicap.redes.tictactoe.game.Board;

public class TicTacToeBoard implements Board {
    private int [] board;

    public TicTacToeBoard() {
        this.board = new int [9];
    }

    @Override
    public void initBoard() {

    }

    @Override
    public void setMove(int piece, int position) {
        if(validateMove(position)) {
            this.board[position] = piece;
        }
    }

    @Override
    public void resetBoard() {
        this.board = new int [9];
    }

    @Override
    public int [] getBoard() {
        return this.board;
    }

    private boolean validateMove(int position){
        return this.board[position] == 0;
    }
}
