package me.dinozoid.server.user;

public class User {

    private String username, uid, rank;

    public User(String username, String uid, String rank) {
        this.username = username;
        this.uid = uid;
        this.rank = rank;
    }

    public String username() {
        return username;
    }

    public void username(String username) {
        this.username = username;
    }

    public String uid() {
        return uid;
    }

    public void uid(String uid) {
        this.uid = uid;
    }

    public String rank() {
        return rank;
    }

    public void rank(String rank) {
        this.rank = rank;
    }
}
