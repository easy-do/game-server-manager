package game.server.manager.server.websocket.handler;


public interface AbstractHandlerService<T> {

    Object handler(T handlerData);
}
