package me.dinozoid.websocket.server.packet.implementations;

import me.dinozoid.websocket.client.packet.ClientPacketHandler;
import me.dinozoid.websocket.server.ServerStart;
import me.dinozoid.websocket.server.packet.Packet;
import me.dinozoid.websocket.server.packet.ServerPacketHandler;
import me.dinozoid.websocket.server.user.User;

public class STitlePacket extends Packet {

    public STitlePacket(String title, String subtitle) {
        this(title, subtitle, 20, 60, 20);
    }

    public STitlePacket(String title, String subtitle, int fadeInTicks, int stayTicks, int fadeOutTicks) {
        super(ServerStart.server().packetHandler().getIDForPacket(STitlePacket.class));
        data.addProperty("title", title);
        data.addProperty("subtitle", subtitle);
        data.addProperty("fade", fadeInTicks);
        data.addProperty("stay", stayTicks);
        data.addProperty("fadeout", fadeOutTicks);
    }

    @Override
    public void process(User user, ServerPacketHandler packetHandler) {

    }

    @Override
    public void process(ClientPacketHandler packetHandler) {
        packetHandler.processTitlePacket(this);
    }

    public String title() {
        return data.get("title").getAsString();
    }
    public String subtitle() {
        return data.get("subtitle").getAsString();
    }
    public int fadeIn() {
        return data.get("fade").getAsInt();
    }
    public int stay() {
        return data.get("stay").getAsInt();
    }
    public int fadeOut() {
        return data.get("fadeout").getAsInt();
    }

}
