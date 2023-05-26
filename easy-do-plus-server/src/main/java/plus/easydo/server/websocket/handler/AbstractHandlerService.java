package plus.easydo.server.websocket.handler;


public interface AbstractHandlerService<T> {

    Object handler(T handlerData);
}
