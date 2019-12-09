package com.unicap.redes.tictactoe.game;

import com.unicap.redes.tictactoe.entities.Client;

public interface Game {
    void initBoard();
    void restart();
    int makeMove(int piece, int position);
    Board getBoard();
    String newPlayer(Client player);
    void logout(Client player);
    int turn();
    boolean isFinished();
    int getWinner();
}
