package com.danzki;

import com.danzki.socket.DBServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
  Run:
  1) mvn clean package spring-boot:repackage
  2) java -jar target/dbserver.jar --server.port=8081 --dbserver.port=8091
 */

@SpringBootApplication
public class DBServerApplication implements CommandLineRunner {
  DBServer dbServer;

  public DBServerApplication(DBServer dbServer) {
    this.dbServer = dbServer;
  }

  public static void main(String[] args) {
    SpringApplication.run(DBServerApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    dbServer.go();
  }
}
