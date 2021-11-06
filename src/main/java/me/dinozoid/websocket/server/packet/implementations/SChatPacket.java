package me.dinozoid.websocket.server.packet.implementations;

import me.dinozoid.websocket.client.Client;
import me.dinozoid.websocket.client.ClientStart;
import me.dinozoid.websocket.client.packet.ClientPacketHandler;
import me.dinozoid.websocket.server.ServerStart;
import me.dinozoid.websocket.server.packet.Packet;
import me.dinozoid.websocket.server.packet.ServerPacketHandler;
import me.dinozoid.websocket.server.user.User;
import org.java_websocket.WebSocket;

public class SChatPacket extends Packet {

    public SChatPacket(User user, String message) {
        super(0);
        data.addProperty("user", ServerStart.server().gson().toJson(user));
        data.addProperty("msg", message);
        System.out.println(data);
    }

    @Override
    public void process(WebSocket ws, ServerPacketHandler packetHandler) {

    }

    @Override
    public void process(Client client, ClientPacketHandler packetHandler) {
        packetHandler.processChatPacket(this);
    }

    public String message() {
        return data.get("msg").getAsString();
    }
    public User user() {
        return ClientStart.client().gson().fromJson(data.get("user").getAsString(), User.class);
    }

}
