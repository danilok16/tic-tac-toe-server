package com.unicap.redes.tictactoe.entities;
import java.net.InetAddress;
import java.util.Objects;

public class Client {
    private int id;
    private String nickname;
    private InetAddress ip;
    private int port;

    public Client() {
    }

    public Client(int id, String nickname, InetAddress ip, int port) {
        this.nickname = nickname;
        this.ip = ip;
        this.port = port;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return port == client.port &&
                nickname.equals(client.nickname) &&
                ip.equals(client.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, ip, port);
    }
}
