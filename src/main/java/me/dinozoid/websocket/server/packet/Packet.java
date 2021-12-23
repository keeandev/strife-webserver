package me.dinozoid.websocket.server.packet;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.dinozoid.websocket.client.Client;
import me.dinozoid.websocket.client.packet.ClientPacketHandler;
import me.dinozoid.websocket.server.user.User;
import org.java_websocket.WebSocket;

import java.util.Map;

public abstract class Packet {

    private int id;
    protected JsonObject data = new JsonObject();

    public Packet(int id) {
        this.id = id;
    }

    public Packet(int id, JsonObject data) {
        this.id = id;
        this.data = data;
        data.addProperty("length", data.toString().length());
        data.addProperty("property-length", data.size());
    }

    public abstract void process(User user, ServerPacketHandler packetHandler);
    public abstract void process(ClientPacketHandler packetHandler);

    public int id() {
        return id;
    }
    public JsonObject data() {
        return data;
    }
    public int propertyLength() {
        return data.get("property-length").getAsInt();
    }
    public int length() {
        return data.get("property").getAsInt();
    }
}
