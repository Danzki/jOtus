package com.danzki;


import com.danzki.core.dao.UserDao;
import com.danzki.core.service.DBServiceUser;
import com.danzki.core.service.DbServiceUserImpl;
import com.danzki.mongo.dao.UserDaoMongo;
import com.danzki.mongo.sessionmanager.SessionManagerMongo;
import com.danzki.mongo.template.MongoBuilder;
import com.danzki.server.UsersWebServer;
import com.danzki.server.UsersWebServerSecurity;
import com.danzki.services.TemplateProcessor;
import com.danzki.services.TemplateProcessorImpl;
import com.danzki.services.UserAuthService;
import com.danzki.services.UserAuthServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerApp {
  private static final int WEB_SERVER_PORT = 8080;
  private static final String TEMPLATES_DIR = "/templates/";

  public static void main(String[] args) throws Exception {
    MongoBuilder mongoBuilder = new MongoBuilder();
    SessionManagerMongo sessionManager = new SessionManagerMongo();
    UserDao userDao = new UserDaoMongo(sessionManager);
    DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

    mongoBuilder.generateUsers(dbServiceUser);

    Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
    UserAuthService authService = new UserAuthServiceImpl(dbServiceUser);

    UsersWebServer usersWebServer = new UsersWebServerSecurity(WEB_SERVER_PORT,
        authService, userDao, gson, templateProcessor);

    usersWebServer.start();
    usersWebServer.join();
  }
}
