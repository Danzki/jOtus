package com.danzki;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class RunnerApplication implements CommandLineRunner {
  private static Logger logger = LoggerFactory.getLogger(RunnerApplication.class);

  @Override
  public void run(String... args) throws Exception {
//    message server
    applicationStarter("hw16-messageServer", "messageserver", 8090, 8080);

//    two dbservers
    applicationStarter("hw16-DBServer", "dbserver", 8091, 8081);
    applicationStarter("hw16-DBServer", "dbserver", 8092, 8082);

//    two frontends
    applicationStarter("hw16-frontend", "frontend", 8093, 8083);
    applicationStarter("hw16-frontend", "frontend", 8094, 8084);
  }

//  public static void main(String[] args) {
//    SpringApplication.run(MessageServerApplication.class, args);
//  }

  private void applicationStarter(String path, String appName, int port, int startingPort) throws IOException {
    logger.info("Starting jarFile on port " + port);
//    java -jar target/<appName>.jar --dbserver.port=<port> --server.port=<startingPort>
    var currentDir = new File("./"+path);
    new ProcessBuilder("java", "-jar", "target/"+appName+".jar", "--"+appName+"."+port, "--server.port="+startingPort)
        .inheritIO()
        .directory(currentDir)
        .start();
  }


}
