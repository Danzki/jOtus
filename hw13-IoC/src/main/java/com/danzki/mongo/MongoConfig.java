package com.danzki.mongo;

import com.danzki.WebAppInitializer;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class, MongoRepositoriesAutoConfiguration.class, MongoDataAutoConfiguration.class})
@PropertySources(value = {@PropertySource("classpath:application.properties")})
public class MongoConfig extends AbstractMongoClientConfiguration {
  private static Logger logger = LoggerFactory.getLogger(WebAppInitializer.class);

  @Value("${mongo.dbname}")
  private String databaseName;

  @Value("${mongo.url}")
  private String url;

  @Override
  @Bean
  public MongoClient mongoClient() {
    logger.info("mongoClient");
    return MongoClients.create(this.url);
  }

  public String getUrl() {
    return url;
  }

  @Bean
  public MongoTemplate mongoTemplate() {
    logger.info("mongoTemplate");
    return new MongoTemplate(mongoDbFactory());
  }

  @Override
  public String getDatabaseName() {
    return databaseName;
  }

}
