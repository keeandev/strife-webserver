package me.dinozoid.websocket.server;

import me.dinozoid.websocket.server.packet.implementations.SChatPacket;
import me.dinozoid.websocket.server.packet.implementations.STitlePacket;

import java.util.Scanner;

public class ServerStart {

    private static Server server;

    public static void main(String[] args) {
        server = new Server(29154);
        server.start();
        Scanner scanner = new Scanner(System.in);
        while(true) {
            if(scanner.hasNextLine()) {
                String next = scanner.nextLine();
                if(next.equalsIgnoreCase("title"))
                    server().packetHandler().broadcastPacket(new STitlePacket("\u00A7cStrife", "\u00A77moment"));
                else server().packetHandler().broadcastPacket(new SChatPacket(server.serverUser, next));
            }
        }
    }

    public static Server server() {
        return server;
    }



}
