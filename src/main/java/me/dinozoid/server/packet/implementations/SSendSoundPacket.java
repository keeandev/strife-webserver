package me.dinozoid.server.packet.implementations;

import me.dinozoid.client.Client;
import me.dinozoid.client.packet.ClientPacketHandler;
import me.dinozoid.server.packet.Packet;
import me.dinozoid.server.packet.ServerPacketHandler;
import org.java_websocket.WebSocket;

import java.util.Arrays;

public class SSendSoundPacket extends Packet {

    public SSendSoundPacket(byte[] bytes) {
        super(4);
        data.addProperty("bytes", Arrays.toString(bytes));
    }

    @Override
    public void process(WebSocket ws, ServerPacketHandler packetHandler) {
    }

    @Override
    public void process(Client client, ClientPacketHandler packetHandler) {
        packetHandler.processSendSoundPacket(this);
    }

    public String bytes() {
        return data.get("bytes").getAsString();
    }
}
