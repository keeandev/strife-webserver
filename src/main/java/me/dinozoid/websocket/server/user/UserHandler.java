package me.dinozoid.websocket.server.user;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.java_websocket.WebSocket;

public class UserHandler {

    private BiMap<WebSocket, User> userMap = HashBiMap.create();

    public void addUser(final WebSocket socket, final User user) {
        userMap.put(socket, user);
    }

    public void removeUser(final WebSocket socket) {
        userMap.remove(socket);
    }

    public void removeUser(final User user) {
        WebSocket socket = userMap.inverse().get(user);
        userMap.remove(socket);
    }

    public User userByUsername(final String username) {
        return userMap.values().stream().filter(user -> user.username().equals(username)).findFirst().orElse(null);
    }

    public void disconnect(final User user) {
        user.socket().close();
        userMap.remove(user);
    }

    public User userBySocket(final WebSocket socket) {
        return userMap.get(socket);
    }

    public WebSocket socket(User user) {
        return userMap.inverse().get(user);
    }

    public BiMap<WebSocket, User> userMap() {
        return userMap;
    }
}
