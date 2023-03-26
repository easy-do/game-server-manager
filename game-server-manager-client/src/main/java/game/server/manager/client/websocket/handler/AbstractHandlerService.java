package game.server.manager.client.websocket.handler;

import game.server.manager.client.model.socket.ServerMessage;

public interface AbstractHandlerService {

    public Void handler(ServerMessage serverMessage);
}
