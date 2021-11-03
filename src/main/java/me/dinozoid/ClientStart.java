package me.dinozoid;

import me.dinozoid.client.Client;
import me.dinozoid.server.packet.implementations.ChatPacket;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class ClientStart {

    public static void main(String[] args) {
        Client client = null;
        try {
            client = new Client(new URI("ws://localhost:29154"));
            client.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        while(true) {
            if(scanner.hasNext())
                client.packetHandler().sendPacket(client, new ChatPacket(scanner.next()));
        }
    }

}
