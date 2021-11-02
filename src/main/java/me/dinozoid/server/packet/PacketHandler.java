package me.dinozoid.server.packet;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import me.dinozoid.server.packet.implementations.AuthenticationSendPacket;

public class PacketHandler {

    private static BiMap<Class<? extends Packet>, Integer> PACKETS = HashBiMap.create();

    public void init() {
        PACKETS.put(AuthenticationSendPacket.class, 0);
    }
    
    public void processAuthenticationSendPacket(AuthenticationSendPacket authenticationSendPacket) {
        
    }

    public Class<? extends Packet> getPacketByID(int id) {
        return PACKETS.inverse().get(id);
    }

    public int getIDForPacket(Class<? extends Packet> packet) {
        return PACKETS.get(packet);
    }
    
}
