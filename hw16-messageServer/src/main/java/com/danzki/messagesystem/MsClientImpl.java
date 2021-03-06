package com.danzki.messagesystem;

import com.danzki.messageSystem.Message;
import com.danzki.messageSystem.MessageType;
import com.danzki.messageSystem.MsClient;
import com.danzki.messageSystem.RequestHandler;
import com.danzki.messageSystem.common.Serializers;
import com.danzki.socket.SocketClientMessageServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class MsClientImpl implements MsClient {
  private static final Logger logger = LoggerFactory.getLogger(MsClientImpl.class);

  private String host;
  private int port;
  private String name;

  private final MessageSystem messageSystem;
  private final Map<String, RequestHandler> handlers = new ConcurrentHashMap<>();

  private SocketClientMessageServer socketClientMessageServer;

  public MsClientImpl(MessageSystem messageSystem) {
    this.messageSystem = messageSystem;
    this.socketClientMessageServer = new SocketClientMessageServer();
  }

  @PostConstruct
  public void postConstruct() {
    messageSystem.addClient(this);
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
    boolean result = messageSystem.newMessage(msg);
    if (!result) {
      logger.error("the last message was rejected: {}", msg);
    }
    return result;
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
    MsClientImpl msClient = (MsClientImpl) o;
    return Objects.equals(name, msClient.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setName(String name) {
    this.name = name;
  }
}
