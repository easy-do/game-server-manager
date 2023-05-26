package plus.easydo.push.client.websocket.handler;

import plus.easydo.push.client.model.socket.ServerMessage;

public interface AbstractHandlerService {

    public Void handler(ServerMessage serverMessage);
}
