package me.dinozoid.websocket.server.packet.implementations;

import me.dinozoid.websocket.client.packet.ClientPacketHandler;
import me.dinozoid.websocket.server.ServerStart;
import me.dinozoid.websocket.server.packet.Packet;
import me.dinozoid.websocket.server.packet.ServerPacketHandler;
import me.dinozoid.websocket.server.user.User;

public class SStaffAnalysisPacket extends Packet {


    public SStaffAnalysisPacket(String verdict) {
        super(ServerStart.server().packetHandler().getIDForPacket(SStaffAnalysisPacket.class));
        data.addProperty("verdict", verdict);
    }

    @Override
    public void process(User user, ServerPacketHandler packetHandler) {

    }

    @Override
    public void process(ClientPacketHandler packetHandler) {

    }

    private String verdict() {
        return data.get("verdict").getAsString();
    }

}
