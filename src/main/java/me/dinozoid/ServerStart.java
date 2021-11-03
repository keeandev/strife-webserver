package me.dinozoid;

import me.dinozoid.server.Server;
import me.dinozoid.server.packet.implementations.ChatPacket;

import java.util.Scanner;

public class ServerStart {

    private static Server server;

    public static void main(String[] args) {
        server = new Server(29154);
        server.start();
        Scanner scanner = new Scanner(System.in);
        while(true) {
            if(scanner.hasNext())
                server().packetHandler().broadcastPacket(new ChatPacket(scanner.next()));
        }
    }

    public static Server server() {
        return server;
    }



}
