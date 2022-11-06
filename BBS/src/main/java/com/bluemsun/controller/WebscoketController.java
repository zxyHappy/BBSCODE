//package com.bluemsun.controller;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//
//import javax.servlet.http.HttpServletRequest;
//import javax.websocket.OnClose;
//import javax.websocket.OnError;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import com.alibaba.fastjson.JSONObject;
//import com.bluemsun.entity.Message;
//import com.bluemsun.service.MessageService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.web.socket.server.standard.SpringConfigurator;
//
//
//@ServerEndpoint(value="/websocket/{fromId}",configurator = SpringConfigurator.class)
//public class WebsocketController {
//
//    private ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
//    MessageService messageService =(MessageService) context.getBean("MessageService");
//
//    //日志记录
//    private Logger logger = LoggerFactory.getLogger(WebsocketController.class);
//    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
//    private static int onlineCount = 0;
//
//    //记录每个用户下多个终端的连接
//    public static Map<String, Set<WebsocketController>> userSocket = new HashMap<>();
//
//    //需要session来对用户发送数据, 获取连接特征fromId
//    private Session session;
//    private String fromId;
//    private String toId;
//
//    /**
//     * @Title: onOpen
//     * @Description: websocekt连接建立时的操作
//     * @param @param fromId 用户id
//     * @param @param session websocket连接的session属性
//     * @param @throws IOException
//     */
//    @OnOpen
//    public void onOpen(@PathParam("fromId") String fromId, Session session, HttpServletRequest request) {
//        this.session = session;
//        this.fromId = String.valueOf(request.getAttribute("id"));
//        onlineCount++;
//        //根据该用户当前是否已经在别的终端登录进行添加操作
//        if (userSocket.containsKey(this.fromId)) {
//            logger.debug("当前用户id:{}已有其他终端登录",this.fromId);
//            userSocket.get(this.fromId).add(this); //增加该用户set中的连接实例
//        }else {
//            logger.debug("当前用户id:{}第一个终端登录",this.fromId);
//            Set<WebsocketController> addUserSet = new HashSet<>();
//            addUserSet.add(this);
//            userSocket.put(this.fromId, addUserSet);
//        }
//        logger.debug("用户{}登录的终端个数是为{}",fromId,userSocket.get(this.fromId).size());
//        logger.debug("当前在线用户数为：{},所有终端个数为：{}",userSocket.size(),onlineCount);
//
//    }
//
//    /**
//     * @Title: onClose
//     * @Description: 连接关闭的操作
//     */
//    @OnClose
//    public void onClose(){
//        //移除当前用户终端登录的websocket信息,如果该用户的所有终端都下线了，则删除该用户的记录
//        if (userSocket.get(this.fromId).size() == 0) {
//            userSocket.remove(this.fromId);
//        }else{
//            userSocket.get(this.fromId).remove(this);
//        }
//        logger.debug("用户{}退出连接！",this.fromId);
//        logger.debug("用户{}登录的终端个数是为{}",this.fromId,userSocket.get(this.fromId).size());
//        userSocket.remove(this.fromId);
//        logger.debug("当前在线用户数为：{},所有终端个数为：{}",userSocket.size(),--onlineCount);
//    }
//
//    /**
//     * @Title: onMessage
//     * @Description: 收到消息后的操作
//     * @param @param message 收到的消息
//     * @param @param session 该连接的session属性
//     */
//    @OnMessage
//    public void onMessage(String message, Session session) {
//        logger.debug("收到来自用户id为：{}的消息：{}",this.fromId,message);
//        JSONObject jsonObject = JSONObject.parseObject(message);//将前台转来的字符串转为json对象
//        this.toId = jsonObject.getString("toId");
//        if(userSocket.containsKey(toId)) {
//            sendMessageToUser(toId, message);
//        }else {
//            logger.debug("用户{}不在线！", toId);
//        }
//        if(session ==null)  logger.debug("session null");
//    }
//
//    /**
//     * @Title: onError
//     * @Description: 连接发生错误时候的操作
//     * @param @param session 该连接的session
//     * @param @param error 发生的错误
//     */
//    @OnError
//    public void onError(Throwable error){
//        logger.debug("用户id为：{}的连接发送错误",this.fromId);
//        error.printStackTrace();
//    }
//
//    /**
//     * @Title: sendMessageToUser
//     * @Description: 发送消息给用户下的所有终端
//     * @param @param fromId 用户id
//     * @param @param message 发送的消息
//     * @param @return 发送成功返回true，反则返回false
//     */
//    public Boolean sendMessageToUser(String toId,String message){
//        messageService.sendMessage(new Message(Integer.parseInt(fromId),Integer.parseInt(toId),message));
//        if (userSocket.containsKey(toId)) {
//            logger.debug(" 给用户id为：{}的所有终端发送消息：{}",toId,message);
//            for (WebsocketController WS : userSocket.get(toId)) {
//                logger.debug("sessionId为:{}",WS.session.getId());
//                try {
//                    WS.session.getBasicRemote().sendText(message);//将消息发送给对方 getBasicRemote().sendText(message) 同步发送
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    logger.debug(" 给用户id为：{}发送消息失败",toId);
//                    return false;
//                }
//            }
//            return true;
//        }
//        logger.debug("发送错误：当前连接不包含id为：{}的用户",toId);
//        return false;
//    }
//
//}
