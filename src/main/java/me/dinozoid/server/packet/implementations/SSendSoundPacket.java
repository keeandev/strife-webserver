package me.dinozoid.server.packet.implementations;

import me.dinozoid.client.Client;
import me.dinozoid.client.packet.ClientPacketHandler;
import me.dinozoid.server.packet.Packet;
import me.dinozoid.server.packet.ServerPacketHandler;
import org.java_websocket.WebSocket;

import java.util.Arrays;
import java.util.Base64;

public class SSendSoundPacket extends Packet {

    public SSendSoundPacket(byte[] bytes) {
        super(4);
        data.addProperty("bytes", Base64.getEncoder().encodeToString(bytes));
    }

    @Override
    public void process(WebSocket ws, ServerPacketHandler packetHandler) {
    }

    @Override
    public void process(Client client, ClientPacketHandler packetHandler) {
        packetHandler.processSendSoundPacket(this);
    }

    public byte[] bytes() {
        return Base64.getDecoder().decode(data.get("bytes").getAsString());
    }
}
