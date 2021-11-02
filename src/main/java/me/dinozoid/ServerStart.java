package me.dinozoid;

import me.dinozoid.client.Client;
import me.dinozoid.server.Server;

import java.net.URI;
import java.net.URISyntaxException;

public class ServerStart {

    public static void main(String[] args) {
        Server server = new Server(29154);
        server.start();
    }

}
