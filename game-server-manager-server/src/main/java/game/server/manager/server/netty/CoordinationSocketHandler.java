//package game.server.manager.server.netty;
//
//import com.alibaba.fastjson2.JSONObject;
//import game.server.manager.common.enums.ChannelTypeEnum;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * @author laoyu
// * @version 1.0
// * @description 自定义消息处理
// * @date 2023/3/23
// */
//@Slf4j
//public class CoordinationSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
//
//
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) {
//         log.info("建立连接，通道开启！");
//    }
//
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) {
//        log.info("断开连接，通道关闭！");
//        //从channelGroup通道组删除
//        CoordinationChannelHandlerPool.removeChannel(ctx.channel());
//    }
//
//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
//        //接收的消息
//        log.info("收到消息{},{}:", ctx.channel().id(), msg.text());
//        // 单独发消息
//        sendMessage(ctx);
//        // 群发消息
////        sendAllMessage();
//    }
//
//    private void sendMessage(ChannelHandlerContext ctx) {
//        String message = "我是服务器，你好呀";
//        ctx.writeAndFlush(new TextWebSocketFrame(message));
//    }
//
//    private void sendAllMessage() {
//        String message = "我是服务器，这是群发消息";
//        CoordinationChannelHandlerPool.clientChannelGroup.writeAndFlush(new TextWebSocketFrame(message));
//    }
//}
