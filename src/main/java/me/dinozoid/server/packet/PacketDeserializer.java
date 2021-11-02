package me.dinozoid.server.packet;

import com.google.gson.*;

import java.lang.reflect.Type;

public class PacketDeserializer<Type> implements JsonDeserializer<Type> {

    private PacketHandler handler;

    public PacketDeserializer(PacketHandler handler) {
        this.handler = handler;
    }

    @Override
    public Type deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int id = jsonObject.get("id").getAsInt();
        return context.deserialize(jsonObject, handler.getPacketByID(id));
    }

}
