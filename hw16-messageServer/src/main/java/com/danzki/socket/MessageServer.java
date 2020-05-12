package com.danzki.socket;

import com.danzki.messageSystem.Message;
import com.danzki.messagesystem.MessageSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class MessageServer {
  private static Logger logger = LoggerFactory.getLogger(MessageServer.class);

  @Value("${messageserver.port}")
  private int port;

  @Autowired
  MessageSystem messageSystem;

  public void go() {
    //DatagramSocket - UDP
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      logger.info("Starting connection on port: {}", port);
      while (!Thread.currentThread().isInterrupted()) {
        logger.info("waiting for client connection on port");
        try (Socket clientSocket = serverSocket.accept()) {
          clientHandler(clientSocket);
        }
      }
    } catch (Exception ex) {
      logger.error("error", ex);
    }
  }

  private void clientHandler(Socket clientSocket) {
    try(ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream())) {
      Message message = (Message) objectInputStream.readObject();
      logger.info("Message received from {} with Id {}", message.getFrom(), message.getId());
      messageSystem.newMessage(message);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }


  }


}
