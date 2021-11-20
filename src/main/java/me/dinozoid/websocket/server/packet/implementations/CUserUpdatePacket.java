package me.dinozoid.websocket.server.packet.implementations;

import com.google.gson.JsonObject;
import me.dinozoid.websocket.client.packet.ClientPacketHandler;
import me.dinozoid.websocket.server.ServerStart;
import me.dinozoid.websocket.server.packet.Packet;
import me.dinozoid.websocket.server.packet.ServerPacketHandler;
import me.dinozoid.websocket.server.user.User;

import java.util.Arrays;

public class CUserUpdatePacket extends Packet {

    public static class Value {
        private final SUserUpdatePacket.UpdateType type;
        private final Object value;
        public Value(final SUserUpdatePacket.UpdateType type, final Object value) {
            this.type = type;
            this.value = value;
        }
        public SUserUpdatePacket.UpdateType type() {
            return type;
        }
        public Object value() {
            return value;
        }
    }

    public CUserUpdatePacket(Value... values) {
        super(ServerStart.server().packetHandler().getIDForPacket(CUserUpdatePacket.class));
        JsonObject object = new JsonObject();
        Arrays.stream(values).forEach(value -> object.addProperty(String.valueOf(value.type), (String)value.value));
        data.add("values", object);
        System.out.println(data);
    }

    @Override
    public void process(User user, ServerPacketHandler packetHandler) {
        packetHandler.processUserUpdatePacket(user, this);
    }

    @Override
    public void process(ClientPacketHandler packetHandler) {

    }

    public JsonObject values() {
        return data.getAsJsonObject("values");
    }

}
