package me.dinozoid.websocket.server.packet;

import com.google.gson.JsonObject;
import me.dinozoid.websocket.client.Client;
import me.dinozoid.websocket.client.packet.ClientPacketHandler;
import org.java_websocket.WebSocket;

public abstract class Packet {

    private int id;
    protected JsonObject data = new JsonObject();

    public Packet(int id) {
        this.id = id;
    }

    public Packet(int id, JsonObject data) {
        this.id = id;
        this.data = data;
    }

    public abstract void process(WebSocket ws, ServerPacketHandler packetHandler);
    public abstract void process(Client client, ClientPacketHandler packetHandler);

    public int id() {
        return id;
    }
    public JsonObject data() {
        return data;
    }
}
