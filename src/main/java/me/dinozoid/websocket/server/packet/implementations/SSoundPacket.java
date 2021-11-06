package me.dinozoid.websocket.server.packet.implementations;

import me.dinozoid.websocket.client.Client;
import me.dinozoid.websocket.client.packet.ClientPacketHandler;
import me.dinozoid.websocket.server.packet.Packet;
import me.dinozoid.websocket.server.packet.ServerPacketHandler;
import me.dinozoid.websocket.server.user.User;
import org.java_websocket.WebSocket;

import java.util.Base64;

public class SSoundPacket extends Packet {

    public SSoundPacket(byte[] bytes) {
        super(3);
        data.addProperty("bytes", Base64.getEncoder().encodeToString(bytes));
    }

    @Override
    public void process(User user, ServerPacketHandler packetHandler) {
    }

    @Override
    public void process(ClientPacketHandler packetHandler) {
        packetHandler.processSendSoundPacket(this);
    }

    public byte[] bytes() {
        return Base64.getDecoder().decode(data.get("bytes").getAsString());
    }
}
