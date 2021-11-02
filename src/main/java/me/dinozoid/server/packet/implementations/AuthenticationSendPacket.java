package me.dinozoid.server.packet.implementations;

import me.dinozoid.server.packet.Packet;
import me.dinozoid.server.packet.PacketHandler;

public class AuthenticationSendPacket extends Packet {

    public AuthenticationSendPacket(String uid, String hwid) {
        super(0);
        data.addProperty("uid", uid);
        data.addProperty("hwid", hwid);
    }

    public AuthenticationSendPacket() {
        this(null, null);
    }

    @Override
    public void process(PacketHandler packetHandler) {
        packetHandler.processAuthenticationSendPacket(this);
    }

    public String uid() {
        return data.get("uid").getAsString();
    }

}
