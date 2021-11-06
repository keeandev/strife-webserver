package me.dinozoid.websocket.server.packet;

import com.google.gson.*;

public class ServerPacketDeserializer<Type> implements JsonDeserializer<Type> {

    private ServerPacketHandler handler;

    public ServerPacketDeserializer(ServerPacketHandler handler) {
        this.handler = handler;
    }

    @Override
    public Type deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int id = jsonObject.get("id").getAsInt();
        return context.deserialize(jsonObject, handler.getPacketByID(id));
    }

}
