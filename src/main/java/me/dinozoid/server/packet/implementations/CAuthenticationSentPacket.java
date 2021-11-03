package me.dinozoid.server.packet.implementations;

import me.dinozoid.client.Client;
import me.dinozoid.client.packet.ClientPacketHandler;
import me.dinozoid.server.packet.Packet;
import me.dinozoid.server.packet.ServerPacketHandler;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;

public class CAuthenticationSentPacket extends Packet {

    public CAuthenticationSentPacket(String uid, String hwid) {
        super(0);
        data.addProperty("uid", uid);
        data.addProperty("hwid", hwid);
    }

    public CAuthenticationSentPacket() {
        this(null, null);
    }

    @Override
    public void process(WebSocket ws, ServerPacketHandler packetHandler) {
        packetHandler.processAuthenticationSentPacket(ws, this);
    }

    @Override
    public void process(Client client, ClientPacketHandler packetHandler) {
    }

    public String uid() {
        return data.get("uid").getAsString();
    }

}
