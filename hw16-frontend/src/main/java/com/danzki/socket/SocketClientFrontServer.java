package com.danzki.socket;

import com.danzki.messageSystem.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ObjectOutputStream;
import java.net.Socket;

@Component
public class SocketClientFrontServer {
  private static Logger logger = LoggerFactory.getLogger(SocketClientFrontServer.class);

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
