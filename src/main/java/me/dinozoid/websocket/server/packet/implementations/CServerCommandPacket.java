package me.dinozoid.websocket.server.packet.implementations;

import me.dinozoid.websocket.client.packet.ClientPacketHandler;
import me.dinozoid.websocket.server.ServerStart;
import me.dinozoid.websocket.server.packet.Packet;
import me.dinozoid.websocket.server.packet.ServerPacketHandler;
import me.dinozoid.websocket.server.user.User;

import java.util.Base64;

public class CServerCommandPacket extends Packet {

    public enum CommandOperation {
        MUTE_USER, UNMUTE_USER, DISCONNECT_USER, LIST_USERS, PACKET
    }

    public CServerCommandPacket(final CommandOperation commandOperation, Object request, String tag) {
        super(ServerStart.server().packetHandler().getIDForPacket(CServerCommandPacket.class));
        data.addProperty("operation", String.valueOf(commandOperation));
        data.addProperty("tag", tag);
        switch (commandOperation) {
            case LIST_USERS:
            case DISCONNECT_USER:
            case UNMUTE_USER:
            case MUTE_USER: {
                data.addProperty("request", (String)request);
                break;
            }
            case PACKET: {
                data.addProperty("request", Base64.getEncoder().encodeToString(ServerStart.server().gson().toJson(request, Packet.class).getBytes()));
                break;
            }
        }
    }

    @Override
    public void process(User user, ServerPacketHandler packetHandler) {
        packetHandler.processServerCommandPacket(user, this);
    }

    @Override
    public void process(ClientPacketHandler packetHandler) {

    }

    public Object request() {
        switch (CommandOperation.valueOf(data.get("operation").getAsString())) {
            case PACKET: {
                return ServerStart.server().gson().fromJson(new String(Base64.getDecoder().decode(data.get("request").getAsString())), Packet.class);
            }
            default: {
                return data.get("request").getAsString();
            }
        }
    }

    public String tag() {
        return data.get("tag").getAsString();
    }
    public CommandOperation operation() {
        return CommandOperation.valueOf(data.get("operation").getAsString());
    }

}
