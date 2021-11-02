package me.dinozoid.server.packet.implementations;

import me.dinozoid.server.packet.Packet;
import me.dinozoid.server.packet.PacketHandler;
import org.java_websocket.WebSocket;

public class SAuthenticationSentPacket extends Packet {

    public SAuthenticationSentPacket(String uid, String hwid) {
        super(0);
        data.addProperty("uid", uid);
        data.addProperty("hwid", hwid);
    }

    public SAuthenticationSentPacket() {
        this(null, null);
    }

    @Override
    public void process(WebSocket ws, PacketHandler packetHandler) {
        packetHandler.processAuthenticationSendPacket(ws, this);
    }

    public String uid() {
        return data.get("uid").getAsString();
    }

}
