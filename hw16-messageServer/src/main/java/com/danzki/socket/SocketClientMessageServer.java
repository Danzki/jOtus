package com.danzki.socket;

import com.danzki.messageSystem.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ObjectOutputStream;
import java.net.Socket;

@Component
public class SocketClientMessageServer {
  private static Logger logger = LoggerFactory.getLogger(SocketClientMessageServer.class);

  public void sendMessage(Message message, String messageHost, int messagePort) {
    try (Socket clientSocket = new Socket(messageHost, messagePort);
         ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())) {
      oos.writeObject(message);
      logger.info("Message with ID [{}] is send to {} via MessageServer", message.getId(), message.getTo());
    } catch (Exception ex) {
      logger.error("error", ex);
    }

  }
}
