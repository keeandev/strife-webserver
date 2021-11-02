package me.dinozoid.server.packet;

import com.google.gson.JsonObject;
import org.java_websocket.WebSocket;
import org.java_websocket.server.WebSocketServer;

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

    public abstract void process(PacketHandler packetHandler);

    public int id() {
        return id;
    }
    public JsonObject data() {
        return data;
    }
}
