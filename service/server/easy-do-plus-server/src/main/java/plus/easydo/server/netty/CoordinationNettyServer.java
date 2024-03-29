//package plus.easydo.server.netty;
//
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.handler.codec.http.HttpObjectAggregator;
//import io.netty.handler.codec.http.HttpServerCodec;
//import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
//import io.netty.handler.stream.ChunkedWriteHandler;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * @author laoyu
// * @version 1.0
// * @description Netty服务
// * @date 2023/3/23
// */
//@Slf4j
//public class CoordinationNettyServer {
//
//    private final int port;
//
//    public CoordinationNettyServer(int port) {
//        this.port = port;
//    }
//
//
//    public void start() throws Exception {
//        EventLoopGroup bossGroup = new NioEventLoopGroup();
//        EventLoopGroup group = new NioEventLoopGroup();
//        try {
//            ServerBootstrap sb = new ServerBootstrap();
//            sb.option(ChannelOption.SO_BACKLOG, 1024);
//            // 绑定线程池
//            sb.group(group, bossGroup)
//                    // 指定使用的channel
//                    .channel(NioServerSocketChannel.class)
//                    // 绑定监听端口
//                    .localAddress(this.port)
//                    // 绑定客户端连接时候触发操作
//                    .childHandler(new ChannelInitializer<SocketChannel>() {
//                        @Override
//                        protected void initChannel(SocketChannel ch){
//                            //websocket协议本身是基于http协议的，所以这边也要使用http解编码器
//                            ch.pipeline().addLast(new HttpServerCodec());
//                            //以块的方式来写的处理器
//                            ch.pipeline().addLast(new ChunkedWriteHandler());
//                            ch.pipeline().addLast(new HttpObjectAggregator(8192));
//                            ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws", "WebSocket", true, 65536 * 10));
//                            //自定义消息处理类
//                            ch.pipeline().addLast(new CoordinationSocketHandler());
//                        }
//                    });
//            ChannelFuture cf = sb.bind().sync(); // 服务器异步创建绑定
//            log.info("netty服务已启动，正在监听： " + cf.channel().localAddress());
//            // 关闭服务器通道
//            cf.channel().closeFuture().sync();
//        } finally {
//            // 释放线程池资源
//            group.shutdownGracefully().sync();
//            bossGroup.shutdownGracefully().sync();
//        }
//    }
//
//}
