package com.danzki.configuration;

import com.danzki.backend.generators.MongoGenerator;
import com.danzki.backend.generators.MongoGeneratorImpl;
import com.danzki.backend.mongo.handlers.GetUserDataRequestHandler;
import com.danzki.core.service.DBServiceUser;
import com.danzki.front.FrontendService;
import com.danzki.front.FrontendServiceImpl;
import com.danzki.front.handlers.GetUserDataResponseHandler;
import com.danzki.messagesystem.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import javax.annotation.PostConstruct;

@Configuration
@ComponentScan
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
  private static Logger logger = LoggerFactory.getLogger(WebConfig.class);

  private final ApplicationContext applicationContext;

  @Autowired
  DBServiceUser dbServiceUser;
  private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
  private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

  public WebConfig(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Bean
  public SpringResourceTemplateResolver templateResolver() {
    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
    templateResolver.setApplicationContext(this.applicationContext);
    templateResolver.setPrefix("/WEB-INF/views/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setCacheable(true);
    templateResolver.setCharacterEncoding("UTF-8");
    return templateResolver;
  }

  @Bean
  public SpringTemplateEngine templateEngine() {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(templateResolver());
    return templateEngine;
  }

  @Bean
  public ThymeleafViewResolver viewResolver() {
    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    viewResolver.setTemplateEngine(templateEngine());
    viewResolver.setOrder(1);
    viewResolver.setCharacterEncoding("UTF-8");
    return viewResolver;
  }

  @Bean
  public MessageSystem messageSystem() {
    var messageSystem = new MessageSystemImpl();
    return messageSystem;
  }

  @Bean
  public MsClient dataBaseMsClient() {
    var databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem());
    return databaseMsClient;
  }

  @Bean
  public MsClient frontMsClient() {
    var frontMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem());
    return frontMsClient;
  }

  @Bean
  public FrontendService frontendService() {
    var frontendService = new FrontendServiceImpl(frontMsClient(), DATABASE_SERVICE_CLIENT_NAME);
    return frontendService;
  }

  @PostConstruct
  private void postConstruct() {
    dataBaseMsClient().addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler(dbServiceUser));
    messageSystem().addClient(dataBaseMsClient());

    frontMsClient().addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler(frontendService()));
    messageSystem().addClient(frontMsClient());

    //drop database
    dbServiceUser.dropDatabase();
    logger.info("Database droped");
    MongoGenerator mongoGenerator = new MongoGeneratorImpl(dbServiceUser);
    mongoGenerator.generateUsers();
    logger.info("loadData: Users generated.");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/**").addResourceLocations("/WEB-INF/static/");
    registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/")
        .resourceChain(false).addResolver(new WebJarsResourceResolver());
  }
}
