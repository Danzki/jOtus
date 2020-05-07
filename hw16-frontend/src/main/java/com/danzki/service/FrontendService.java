package com.danzki.service;


import com.danzki.messagesystem.Message;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public interface FrontendService {
  void getUserData(long userId, Consumer<String> dataConsumer);
  void saveUserData(String message, Consumer<String> dataConsumer);
  <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass);
  public void sendFrontMessage(Message message);
  public void getUserAll(String message, Consumer<String> dataConsumer);
}

