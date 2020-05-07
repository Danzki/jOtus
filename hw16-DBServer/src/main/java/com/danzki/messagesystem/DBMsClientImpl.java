package com.danzki.messagesystem;

import com.danzki.socket.SocketClientDBServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.danzki.messagesystem.common.Serializers;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class DBMsClientImpl implements MsClient {
  private static final Logger logger = LoggerFactory.getLogger(DBMsClientImpl.class);

  private final String name;
  private final SocketClientDBServer socketClientDBServer;
  private final Map<String, RequestHandler> handlers = new ConcurrentHashMap<>();


  public DBMsClientImpl(String name, SocketClientDBServer socketClientDBServer) {
    logger.info("Creating new DB Ms Client with name " + name);
    this.name = name;
    this.socketClientDBServer = socketClientDBServer;
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
    socketClientDBServer.sendMessage(msg);
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
    DBMsClientImpl msClient = (DBMsClientImpl) o;
    return Objects.equals(name, msClient.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
