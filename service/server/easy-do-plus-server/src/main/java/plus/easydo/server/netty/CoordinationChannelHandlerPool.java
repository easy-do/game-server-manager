//package plus.easydo.server.netty;
//
//import enums.plus.easydo.common.ChannelTypeEnum;
//import exception.plus.easydo.common.ExceptionFactory;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelId;
//import io.netty.channel.group.ChannelGroup;
//import io.netty.channel.group.DefaultChannelGroup;
//import io.netty.util.concurrent.GlobalEventExecutor;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.Objects;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//
///**
// * @author laoyu
// * @version 1.0
// * @description 处理池
// * @date 2023/3/23
// */
//@Slf4j
//public class CoordinationChannelHandlerPool {
//
//    public CoordinationChannelHandlerPool() {
//    }
//
//    /** 通道存储 */
//    public static ConcurrentMap<String, Channel> channelIdMap = new ConcurrentHashMap<>();
//
//    /** 客户端通道 */
//    public static ConcurrentMap<String, String> clientChannelMap = new ConcurrentHashMap<>();
//
//    /** 游览器通道 */
//    public static ConcurrentMap<String, String> browserClientChannelMap = new ConcurrentHashMap<>();
//
//
//
//    //channelGroup通道组
//    public static ChannelGroup clientChannelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
//
//    public static ChannelGroup browserChannelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
//
//
//    public static void removeChannel(Channel channel){
//        boolean result = browserChannelGroup.remove(channel);
//        if (!result) {
//            result = clientChannelGroup.remove(channel);
//            log.info("删除客户端通道:{}",result);
//        }
//        log.info("删除游览器通道:{}",result);
//    }
//
//    public static Channel getChannelByClientId(String clientId){
//        String channelId = clientChannelMap.get(clientId);
//        if(Objects.nonNull(channelId)){
//            Channel channel = channelIdMap.get(channelId);
//            if(Objects.nonNull(channel)){
//                return channel;
//            }
//        }
//        throw ExceptionFactory.bizException("客户端通道不存在");
//    }
//
//    public static Channel getClientChannelByBrowserId(String browserId){
//        String channelId = browserClientChannelMap.get(browserId);
//        if(Objects.nonNull(channelId)){
//            Channel channel = channelIdMap.get(channelId);
//            if(Objects.nonNull(channel)){
//                return channel;
//            }
//        }
//        throw ExceptionFactory.bizException("游览器对应客户端通道不存在");
//    }
//}
