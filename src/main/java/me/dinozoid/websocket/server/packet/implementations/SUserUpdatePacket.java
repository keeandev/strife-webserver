package me.dinozoid.websocket.server.packet.implementations;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.dinozoid.websocket.client.packet.ClientPacketHandler;
import me.dinozoid.websocket.server.ServerStart;
import me.dinozoid.websocket.server.packet.Packet;
import me.dinozoid.websocket.server.packet.ServerPacketHandler;
import me.dinozoid.websocket.server.user.User;

import java.util.Arrays;
import java.util.Map;

public class SUserUpdatePacket extends Packet {

    public enum UpdateType {
        ACCOUNT_USERNAME, CLIENT_USERNAME, UID, RANK
    }

    public static class Value {
        private final UpdateType type;
        private final Object value;
        public Value(UpdateType type, Object value) {
            this.type = type;
            this.value = value;
        }
        public UpdateType type() {
            return type;
        }
        public Object value() {
            return value;
        }
    }

    public SUserUpdatePacket(Value... values) {
        super(ServerStart.server().packetHandler().getIDForPacket(SUserUpdatePacket.class));
        JsonObject object = new JsonObject();
        Arrays.stream(values).forEach(value -> object.addProperty(String.valueOf(value.type), String.valueOf(value.value)));
        data.add("values", object);
    }

    @Override
    public void process(User user, ServerPacketHandler packetHandler) {

    }

    @Override
    public void process(ClientPacketHandler packetHandler) {
        packetHandler.processUserUpdatePacket(this);
    }

    public JsonObject values() {
        return data.getAsJsonObject("values");
    }

}
