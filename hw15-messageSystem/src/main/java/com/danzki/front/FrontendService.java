package com.danzki.front;


import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public interface FrontendService {
  void getUserData(long userId, Consumer<String> dataConsumer);

  void saveUserData(String message, Consumer<String> dataConsumer);

  <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass);
}

