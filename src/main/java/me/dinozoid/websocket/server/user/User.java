package me.dinozoid.websocket.server.user;

import me.dinozoid.websocket.server.ServerStart;
import org.java_websocket.WebSocket;

public class User {

    private String username, uid, rank;

    public User(final String username, final String uid, final String rank) {
        this.username = username;
        this.uid = uid;
        this.rank = rank;
    }

    public WebSocket socket() {
        return ServerStart.server().userHandler().userByUsername(username).socket();
    }
    public String username() {
        return username;
    }
    public void username(final String username) {
        this.username = username;
    }
    public String uid() {
        return uid;
    }
    public void uid(final String uid) {
        this.uid = uid;
    }
    public String rank() {
        return rank;
    }
    public void rank(final String rank) {
        this.rank = rank;
    }
}
