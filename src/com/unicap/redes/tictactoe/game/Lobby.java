package com.unicap.redes.tictactoe.game;

import com.unicap.redes.tictactoe.entities.Client;

public interface Lobby {
    void enter (Client client);
    void leave (Client client);
    Game startGame(Client player1, Client player2);
}
