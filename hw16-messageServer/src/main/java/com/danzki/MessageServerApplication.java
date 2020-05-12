package com.danzki;

import com.danzki.socket.MessageServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
  Run:
  1) mvn clean package spring-boot:repackage
  2) java -jar target/messageserver.jar --messageserver.port=8090
 */

@SpringBootApplication
public class MessageServerApplication implements CommandLineRunner {

  private MessageServer messageServer;

  public MessageServerApplication(MessageServer messageServer) {
    this.messageServer = messageServer;
  }

  public static void main(String[] args) {
    SpringApplication.run(MessageServerApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    messageServer.go();
  }
}
