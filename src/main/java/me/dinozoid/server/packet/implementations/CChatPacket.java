package me.dinozoid.server.packet.implementations;

import com.google.gson.JsonObject;
import me.dinozoid.server.packet.Packet;
import me.dinozoid.server.packet.PacketHandler;
import org.java_websocket.WebSocket;

public class CChatPacket extends Packet {

    public CChatPacket(String message) {
        super(2);
        data.addProperty("msg", message);
    }

    @Override
    public void process(WebSocket ws, PacketHandler packetHandler) {
        packetHandler.processChatPacket(ws, this);
    }

    public String message() {
        return data.get("msg").getAsString();
    }

}
