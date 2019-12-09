package com.unicap.redes.tictactoe.common;

import java.io.Serializable;

public class TransferObject implements Serializable {
    private static final long serialVersionUID = 1L;
    private int code;
    private String message;
    private int turn;

    public TransferObject() {
    }

    public TransferObject(int code, String message, int turn) {
        this.code = code;
        this.message = message;
        this.turn = turn;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    @Override
    public String toString() {
        return "TransferObject{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", turn=" + turn +
                '}';
    }
}
