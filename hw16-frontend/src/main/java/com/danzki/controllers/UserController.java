package com.danzki.controllers;

import com.danzki.service.FrontendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
  private static Logger logger = LoggerFactory.getLogger(UserController.class);

  private final FrontendService frontendService;
  private final SimpMessagingTemplate simpMessagingTemplate;

  public UserController(FrontendService frontendService, SimpMessagingTemplate simpMessagingTemplate) {
    this.frontendService = frontendService;
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  @MessageMapping("/getUserList")
  public String getUserList(String message) {
    logger.info("Message from Frontend: " + message);
    frontendService.getUserAll(message, userListData-> {
      logger.info("DBService ответил сообщением: {}", userListData);
      sendWebSocketMessage(userListData);
    });
    return "userList";
  }

  @GetMapping("/user/create")
  public String userCreateView() {
    return "create";
  }

  @GetMapping("/user/list")
  public String userListView() {
    return "userList";
  }

  @MessageMapping("/userSaveMessage")
  public void userSave(String message) {
    logger.info("Message from Frontend: " + message);
    frontendService.saveUserData(message, userData-> {
      logger.info("DB service response: " + userData);
      sendWebSocketMessage(userData);
    });
  }

  private void sendWebSocketMessage(String frontMessage) {
    simpMessagingTemplate.convertAndSend("/topic/DBServiceResponse", frontMessage);
  }

}
