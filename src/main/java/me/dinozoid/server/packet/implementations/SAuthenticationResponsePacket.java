package me.dinozoid.server.packet.implementations;

import me.dinozoid.client.Client;
import me.dinozoid.client.packet.ClientPacketHandler;
import me.dinozoid.server.packet.Packet;
import me.dinozoid.server.packet.ServerPacketHandler;
import org.java_websocket.WebSocket;

public class SAuthenticationResponsePacket extends Packet {

    public SAuthenticationResponsePacket(boolean successful) {
        super(1);
        data.addProperty("successful", successful);
    }

    public SAuthenticationResponsePacket() {
        this(false);
    }

    @Override
    public void process(WebSocket ws, ServerPacketHandler packetHandler) {
    }

    @Override
    public void process(Client client, ClientPacketHandler packetHandler) {
        packetHandler.processAuthenticationResponsePacket(this);
    }

    public boolean successful() {
        return data.get("successful").getAsBoolean();
    }
}
