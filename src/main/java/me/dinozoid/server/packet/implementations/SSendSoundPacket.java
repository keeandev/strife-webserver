package me.dinozoid.server.packet.implementations;

import me.dinozoid.server.packet.Packet;
import me.dinozoid.server.packet.PacketHandler;
import org.java_websocket.WebSocket;

public class SSendSoundPacket extends Packet {
    public SSendSoundPacket(byte[] bytes) {
        super(4);
        data.addProperty("bytes", bytes.toString());
    }

    @Override
    public void process(WebSocket ws, PacketHandler packetHandler) {
        packetHandler.processSendSoundPacket(ws, this);
    }

    public String bytes() {
        return data.get("bytes").getAsString();
    }
}
