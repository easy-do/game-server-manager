let websocket;

function createWebSocket(socketAddress,message,onessage){
    websocket = new WebSocket(socketAddress);
    websocket.onopen = function () {
      console.log('开启websocket连接.')
      websocket.send(message);
    }
    websocket.onerror = function () {
      console.log('websocket连接异常.')
    };
    websocket.onclose = function (e) {
      console.log('websocket 断开: ' + e.code + ' ' + e.reason + ' ' + e.wasClean)
    }
    websocket.onmessage = function (event) {
      console.log('接收到服务端消息:' + event.data)
      onessage(event)
    }
}

//关闭连接
let closeWebSocket=()=> {
    websocket && websocket.close();
}

export {
    websocket,
    createWebSocket,
    closeWebSocket
};
