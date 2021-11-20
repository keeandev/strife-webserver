package me.dinozoid.websocket.server.packet.implementations;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.dinozoid.websocket.client.packet.ClientPacketHandler;
import me.dinozoid.websocket.server.ServerStart;
import me.dinozoid.websocket.server.packet.Packet;
import me.dinozoid.websocket.server.packet.ServerPacketHandler;
import me.dinozoid.websocket.server.user.User;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class SServerCommandPacket extends Packet {

    public SServerCommandPacket(final CServerCommandPacket.CommandOperation commandOperation, Object response) {
        super(ServerStart.server().packetHandler().getIDForPacket(SServerCommandPacket.class));
        data.addProperty("operation", String.valueOf(commandOperation));
        switch (commandOperation) {
            case PACKET: {
                data.addProperty("response", Base64.getEncoder().encodeToString(ServerStart.server().gson().toJson(response, Packet.class).getBytes()));
            }
            case LIST_USERS: {
                JsonObject client = new JsonObject();
                ServerStart.server().userHandler().userMap().values().forEach(user -> client.addProperty("user", ServerStart.server().gson().toJson(user, User.class)));
                data.add("response", client);
            }
            default: {
                data.addProperty("response", (String)response);
            }
        }
    }

    @Override
    public void process(User user, ServerPacketHandler packetHandler) {

    }

    @Override
    public void process(ClientPacketHandler packetHandler) {
        packetHandler.processServerCommandPacket(this);
    }

    public Object response() {
        switch (CServerCommandPacket.CommandOperation.valueOf(data.get("operation").getAsString())) {
            case PACKET: {
                return ServerStart.server().gson().fromJson(new String(Base64.getDecoder().decode(data.get("response").getAsString())), Packet.class);
            }
            case LIST_USERS: {
                List<User> clientUsers = new ArrayList<>();
                for (Map.Entry<String, JsonElement> entry : data.get("response").getAsJsonObject().entrySet()) {
                    clientUsers.add(ServerStart.server().gson().fromJson(entry.getValue(), User.class));
                }
                return clientUsers;
            }
            default: {
                return data.get("response").getAsString();
            }
        }
    }

    public CServerCommandPacket.CommandOperation operation() {
        return CServerCommandPacket.CommandOperation.valueOf(data.get("operation").getAsString());
    }

}
