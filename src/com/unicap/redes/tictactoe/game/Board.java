package com.unicap.redes.tictactoe.game;

public interface Board {
    void initBoard();
    void setMove(int piece, int position);
    void resetBoard();
    int [] getBoard();
}