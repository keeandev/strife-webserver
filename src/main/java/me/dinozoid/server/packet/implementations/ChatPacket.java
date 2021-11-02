package me.dinozoid.server.packet.implementations;

import com.google.gson.JsonObject;
import me.dinozoid.server.packet.Packet;
import me.dinozoid.server.packet.PacketHandler;

public class ChatPacket extends Packet {

    public ChatPacket() {
        super(0);

    }

    @Override
    public void process(PacketHandler packetHandler) {

    }
}
