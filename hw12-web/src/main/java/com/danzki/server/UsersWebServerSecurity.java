package com.danzki.server;

import com.danzki.core.dao.UserDao;
import com.danzki.helpers.FileSystemHelper;
import com.danzki.services.TemplateProcessor;
import com.danzki.services.UserAuthService;
import com.danzki.servlet.*;
import com.google.gson.Gson;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.Arrays;

public class UsersWebServerSecurity implements UsersWebServer {
  private static final String START_PAGE_NAME = "index.html";
  private static final String COMMON_RESOURCES_DIR = "static";

  private final UserDao userDao;
  private final Gson gson;
  protected final TemplateProcessor templateProcessor;
  private final Server server;

  private final UserAuthService authService;

  public UsersWebServerSecurity(int port,
                                UserAuthService authService,
                                UserDao userDao,
                                Gson gson,
                                TemplateProcessor templateProcessor) {
    this.userDao = userDao;
    this.gson = gson;
    this.templateProcessor = templateProcessor;
    this.authService = authService;
    server = new Server(port);
  }

  @Override
  public void start() throws Exception {
    if (server.getHandlers().length == 0) {
      initContext();
    }
    server.start();
  }

  @Override
  public void join() throws Exception {
    server.join();
  }

  @Override
  public void stop() throws Exception {
    server.stop();
  }

  private Server initContext() {

    ResourceHandler resourceHandler = createResourceHandler();
    ServletContextHandler servletContextHandler = createServletContextHandler();

    HandlerList handlers = new HandlerList();
    handlers.addHandler(resourceHandler);
    handlers.addHandler(applySecurity(servletContextHandler, "/users", "/api/user/*", "/admin", "/create"));

    server.setHandler(handlers);
    return server;
  }

  protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
    servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, authService)), "/login");
    AuthorizationFilter authorizationFilter = new AuthorizationFilter();
    Arrays.stream(paths).forEachOrdered(path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
    return servletContextHandler;
  }

  private ResourceHandler createResourceHandler() {
    ResourceHandler resourceHandler = new ResourceHandler();
    resourceHandler.setDirectoriesListed(false);
    resourceHandler.setWelcomeFiles(new String[]{START_PAGE_NAME});
    resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
    return resourceHandler;
  }

  private ServletContextHandler createServletContextHandler() {
    ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    servletContextHandler.addServlet(new ServletHolder(new AdminServlet(templateProcessor, userDao)), "/admin");
    servletContextHandler.addServlet(new ServletHolder(new CreateServlet(templateProcessor, userDao)), "/create");
    return servletContextHandler;
  }
}
