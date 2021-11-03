package me.dinozoid.server.packet.implementations;

import me.dinozoid.client.Client;
import me.dinozoid.client.packet.ClientPacketHandler;
import me.dinozoid.server.packet.Packet;
import me.dinozoid.server.packet.ServerPacketHandler;
import org.java_websocket.WebSocket;

public class ChatPacket extends Packet {

    public ChatPacket(String message) {
        super(2);
        data.addProperty("msg", message);
    }

    @Override
    public void process(WebSocket ws, ServerPacketHandler serverPacketHandler) {
        serverPacketHandler.processChatPacket(ws, this);
    }

    @Override
    public void process(Client client, ClientPacketHandler packetHandler) {
        packetHandler.processChatPacket(this);
    }

    public String message() {
        return data.get("msg").getAsString();
    }

}
