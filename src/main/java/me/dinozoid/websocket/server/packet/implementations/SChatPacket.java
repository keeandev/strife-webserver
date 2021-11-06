package me.dinozoid.websocket.server.packet.implementations;

import me.dinozoid.websocket.client.Client;
import me.dinozoid.websocket.client.packet.ClientPacketHandler;
import me.dinozoid.websocket.server.packet.Packet;
import me.dinozoid.websocket.server.packet.ServerPacketHandler;
import org.java_websocket.WebSocket;

public class SChatPacket extends Packet {

    public SChatPacket(String username, String message) {
        super(0);
        data.addProperty("user", username);
        data.addProperty("msg", message);
        System.out.println(data);
    }

    @Override
    public void process(WebSocket ws, ServerPacketHandler packetHandler) {

    }

    @Override
    public void process(Client client, ClientPacketHandler packetHandler) {
        packetHandler.processChatPacket(this);
    }

    public String message() {
        return data.get("msg").getAsString();
    }
    public String username() {
        return data.get("user").getAsString();
    }

}
