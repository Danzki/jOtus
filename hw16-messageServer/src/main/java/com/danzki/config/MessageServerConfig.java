package com.danzki.config;

import com.danzki.messageSystem.MsClient;
import com.danzki.messagesystem.MessageSystem;
import com.danzki.messagesystem.MsClientImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageServerConfig {

  @Bean
  @ConfigurationProperties(prefix = "dbserver")
  public MsClient dataBaseMsClient(MessageSystem messageSystem) {
    var databaseMsClient = new MsClientImpl(messageSystem);
    return databaseMsClient;
  }

  @Bean
  @ConfigurationProperties(prefix = "frontend")
  public MsClient frontMsClient(MessageSystem messageSystem) {
    var frontMsClient = new MsClientImpl(messageSystem);
    return frontMsClient;
  }

}
