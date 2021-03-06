package com.danzki.configuration;

import com.danzki.backend.mongo.handlers.GetUserDataRequestHandler;
import com.danzki.core.service.DBServiceUser;
import com.danzki.messageSystem.MessageType;
import com.danzki.messageSystem.MsClient;
import com.danzki.messagesystem.DBMsClientImpl;
import com.danzki.socket.SocketClientDBServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class BackendServerConfig {
  private static Logger logger = LoggerFactory.getLogger(BackendServerConfig.class);

  private final ApplicationContext applicationContext;

  @Autowired
  DBServiceUser dbServiceUser;
  @Value("${dbserver.name}")
  private String databaseService;

  public BackendServerConfig(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Bean
  public SocketClientDBServer socketClientDBServer() {
    return new SocketClientDBServer();
  }

  @Bean
  public MsClient dataBaseMsClient() {
    var databaseMsClient = new DBMsClientImpl(databaseService, socketClientDBServer());
    databaseMsClient.addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler(dbServiceUser));
    return databaseMsClient;
  }

}
