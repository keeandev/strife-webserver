package me.dinozoid.websocket.server;

import me.dinozoid.websocket.server.packet.implementations.SChatPacket;
import me.dinozoid.websocket.server.packet.implementations.SRetardFuckerPacket;
import me.dinozoid.websocket.server.packet.implementations.SSoundPacket;
import me.dinozoid.websocket.server.packet.implementations.STitlePacket;
import me.dinozoid.websocket.server.user.User;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.logging.Logger;

public class ServerStart {

    private static Server server;

    private static Thread shutdownHook = new Thread(() -> {
        try {
            server.stop();
            System.out.println("Server has been stopped.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });


    public static void main(String[] args) throws InterruptedException {
        server = new Server(30734);
        server.start();
        Runtime.getRuntime().addShutdownHook(shutdownHook);
        Scanner scanner = new Scanner(System.in);
        while(true) {
            if(scanner.hasNextLine()) {
                String next = scanner.nextLine();
                String[] split = next.split(" ");
                switch (split[0]) {
                    case "title": {
                        String title = "\u00A7cStrife";
                        String subtitle = "\u00A77moment";
                        if(split.length > 1) {
                            User user = server.userHandler().userByUsername(split[1]);
                            if(split.length > 2) title = split[2];
                            if(split.length > 3) subtitle = split[3];
                            if(user != null) {
                                server.packetHandler().sendPacket(user, new STitlePacket(title.replaceAll("_", " "), subtitle.replaceAll("_", " ")));
                            } else {
                                System.out.println("User not found.");
                            }
                        } else server.packetHandler().broadcastPacket(new STitlePacket(title.replaceAll("_", " "), subtitle.replaceAll("_", " ")));
                        break;
                    }
                    case "retard": {
                        if(split.length > 1) {
                            User user = server.userHandler().userByUsername(split[1]);
                            if(user != null) {
                                server.packetHandler().sendPacket(user, new SRetardFuckerPacket(1.0E-9F));
                            } else {
                                System.out.println("User not found.");
                            }
                        }
                        break;
                    }
                    case "server": {
                        if(split.length > 1) {
                            switch (split[1]) {
                                case "shutdown": {
                                    server.stop();
                                    System.out.println("Server has been stopped.");
                                    break;
                                }
                            }
                        }
                    }
                    case "disconnect": {
                        if(split.length > 1) {
                            User user = server.userHandler().userByUsername(split[1]);
                            if(user != null) {
                                server.userHandler().disconnect(user);
                            } else System.out.println("User not found on server.");
                        }
                    }
                    default: {
                        server.packetHandler().broadcastPacket(new SChatPacket(server.serverUser(), next));
                        break;
                    }
                }
            }
        }
    }

    public static Server server() {
        return server;
    }
}
