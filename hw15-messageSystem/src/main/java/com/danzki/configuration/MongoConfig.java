package com.danzki.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class, MongoRepositoriesAutoConfiguration.class, MongoDataAutoConfiguration.class})
@PropertySources(value = {@PropertySource("classpath:application.properties")})
@ComponentScan("com.danzki")
public class MongoConfig extends AbstractMongoClientConfiguration {
  private static Logger logger = LoggerFactory.getLogger(MongoConfig.class);

  @Value("${mongo.dbname}")
  private String databaseName;

  @Value("${mongo.url}")
  private String url;

  @Override
  @Bean
  public MongoClient mongoClient() {
    return MongoClients.create(this.url);
  }

  public String getUrl() {
    return url;
  }

  @Bean
  public MongoTemplate mongoTemplate() {
    return new MongoTemplate(mongoDbFactory());
  }

  @Override
  public String getDatabaseName() {
    return databaseName;
  }

}
