package me.dinozoid;

import me.dinozoid.client.Client;

import java.net.URI;
import java.net.URISyntaxException;

public class ClientStart {

    public static void main(String[] args) {
        try {
            Client client = new Client(new URI("ws://localhost:3000"));
            client.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
