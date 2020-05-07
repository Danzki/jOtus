package com.danzki.socket;

import com.danzki.messagesystem.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClientDBServer {
  private static Logger logger = LoggerFactory.getLogger(SocketClientDBServer.class);

  @Value("${messageServer.host}")
  private String messageServerHost;
  @Value("${messageServer.port}")
  private int messageServerPort;

  public void sendMessage(Message message) {
    try (Socket clientSocket = new Socket(messageServerHost, messageServerPort);
         ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())) {
      oos.writeObject(message);
      logger.info("Message with ID [{}] is send to {} via MessageServer", message.getId(), message.getTo());
    } catch (Exception ex) {
      logger.error("error", ex);
    }

  }
}
