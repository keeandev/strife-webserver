package me.dinozoid.server.packet.implementations;

import me.dinozoid.server.packet.Packet;
import me.dinozoid.server.packet.PacketHandler;
import org.java_websocket.WebSocket;

public class CAuthenticationResponsePacket extends Packet {

    public CAuthenticationResponsePacket(boolean successful) {
        super(1);
        data.addProperty("successful", successful);
    }

    public CAuthenticationResponsePacket() {
        this(false);
    }

    @Override
    public void process(WebSocket ws, PacketHandler packetHandler) {
        packetHandler.processAuthenticationResponsePacket(ws, this);
    }

    public boolean successful() {
        return data.get("successful").getAsBoolean();
    }

}
