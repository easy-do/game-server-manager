package plus.easydo.client.websocket.handler;


import plus.easydo.client.model.socket.ServerMessage;

public interface AbstractHandlerService {

    public Void handler(ServerMessage serverMessage);
}
