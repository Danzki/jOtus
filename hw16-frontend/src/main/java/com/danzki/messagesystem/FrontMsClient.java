package com.danzki.messagesystem;

import com.danzki.messageSystem.Message;
import com.danzki.messageSystem.MessageType;
import com.danzki.messageSystem.MsClient;
import com.danzki.messageSystem.RequestHandler;
import com.danzki.messageSystem.common.Serializers;
import com.danzki.socket.SocketClientFrontServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FrontMsClient implements MsClient {
  private static final Logger logger = LoggerFactory.getLogger(FrontMsClient.class);

  private final String name;
  private final SocketClientFrontServer socketClientFrontServer;
  private final Map<String, RequestHandler> handlers = new ConcurrentHashMap<>();


  public FrontMsClient(@Value("${frontend.name}") String name, SocketClientFrontServer socketClientFrontServer) {
    this.name = name;
    this.socketClientFrontServer = socketClientFrontServer;
  }

  @Override
  public void addHandler(MessageType type, RequestHandler requestHandler) {
    this.handlers.put(type.getValue(), requestHandler);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean sendMessage(Message msg) {
    socketClientFrontServer.sendMessage(msg);
    return true;
  }

  @Override
  public void handle(Message msg) {
    logger.info("new message:{}", msg);
    try {
      RequestHandler requestHandler = handlers.get(msg.getType());
      if (requestHandler != null) {
        requestHandler.handle(msg).ifPresent(this::sendMessage);
      } else {
        logger.error("handler not found for the message type:{}", msg.getType());
      }
    } catch (Exception ex) {
      logger.error("msg:" + msg, ex);
    }
  }

  @Override
  public <T> Message produceMessage(String to, T data, MessageType msgType) {
    return new Message(name, to, null, msgType.getValue(), Serializers.serialize(data));
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FrontMsClient msClient = (FrontMsClient) o;
    return Objects.equals(name, msClient.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
