package com.danzki;

import com.danzki.socket.FrontServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
  Run:
  1) mvn clean package spring-boot:repackage
  2) java -jar target/frontend.jar --server.port=8083 --frontend.port=8093
 */

@SpringBootApplication
public class FrontendApplication implements CommandLineRunner {
  FrontServer frontServer;

  public FrontendApplication(FrontServer frontServer) {
    this.frontServer = frontServer;
  }

  public static void main(String[] args) {
    SpringApplication.run(FrontendApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    frontServer.go();
  }
}
