package me.dinozoid.websocket.client;

import me.dinozoid.websocket.server.packet.implementations.CChatPacket;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class ClientStart {

    private static Client client;

    public static void main(String[] args) {
        try {
            client = new Client(new URI("ws://localhost:29154/?uid=0000&hwid=813941c372e65e11dd98f4f836ce736d"));
            client.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        while(true) {
            if(scanner.hasNext())
                client.packetHandler().sendPacket(client, new CChatPacket(scanner.next()));
        }
    }

    public static Client client() {
        return client;
    }
}
