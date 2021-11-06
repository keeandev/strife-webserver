package me.dinozoid.websocket.server;

import me.dinozoid.websocket.server.packet.implementations.SChatPacket;

import java.util.Scanner;

public class ServerStart {

    private static Server server;

    public static void main(String[] args) {
        server = new Server(29154);
        server.start();
        Scanner scanner = new Scanner(System.in);
        while(true) {
            if(scanner.hasNext())
                server().packetHandler().broadcastPacket(new SChatPacket(server.serverUser, scanner.next()));
        }
    }

    public static Server server() {
        return server;
    }



}
