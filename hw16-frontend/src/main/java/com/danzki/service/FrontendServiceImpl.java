package com.danzki.service;

import com.danzki.messageSystem.Message;
import com.danzki.messageSystem.MessageType;
import com.danzki.messageSystem.MsClient;
import com.danzki.messageSystem.common.Serializers;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Component
public class FrontendServiceImpl implements FrontendService {
  private static final Logger logger = LoggerFactory.getLogger(FrontendServiceImpl.class);

  private final Map<UUID, Consumer<?>> consumerMap = new ConcurrentHashMap<>();
  private final MsClient msClient;
  private final String databaseServiceClientName;

  private final JsonParser jsonParser = new JsonParser();

  public FrontendServiceImpl(MsClient msClient, @Value("${dbserver.name}") String databaseServiceClientName) {
    this.msClient = msClient;
    this.databaseServiceClientName = databaseServiceClientName;
  }

  @Override
  public void getUserData(long userId, Consumer<String> dataConsumer) {
    Message outMsg = msClient.produceMessage(databaseServiceClientName, userId, MessageType.USER_DATA);
    consumerMap.put(outMsg.getId(), dataConsumer);
    msClient.sendMessage(outMsg);
  }

  @Override
  public void getUserAll(String message, Consumer<String> dataConsumer) {
    Message outMsg = msClient.produceMessage(databaseServiceClientName, "", MessageType.USER_DATA);
    consumerMap.put(outMsg.getId(), dataConsumer);
    msClient.sendMessage(outMsg);
  }

  @Override
  public void saveUserData(String message, Consumer<String> dataConsumer) {
    JsonObject jsonObject = jsonParser.parse(message).getAsJsonObject();

    JsonObject messageStr = jsonObject.getAsJsonObject("messageStr");
    logger.info("messageStr: {}", messageStr);

    String jsonUserData = messageStr.toString();

    Message outMsg = msClient.produceMessage(databaseServiceClientName, jsonUserData, MessageType.USER_DATA);
    consumerMap.put(outMsg.getId(), dataConsumer);
    msClient.sendMessage(outMsg);
  }

  @Override
  public <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass) {
    Consumer<T> consumer = (Consumer<T>) consumerMap.remove(sourceMessageId);
    if (consumer == null) {
      logger.warn("consumer not found for:{}", sourceMessageId);
      return Optional.empty();
    }
    return Optional.of(consumer);
  }

  @Override
  public void sendFrontMessage(Message message) {
    logger.info("new message:{}", message);
    try {
      String userJsonData = Serializers.deserialize(message.getPayload(), String.class);
      Optional<UUID> optSourceMessageId = message.getSourceMessageId();
      optSourceMessageId.ifPresentOrElse(
          sourceMessageId -> takeConsumer(sourceMessageId, String.class).ifPresent(consumer -> consumer.accept(userJsonData)),
          () -> new RuntimeException("Not found sourceMsg for message:" + message.getId())
      );
    } catch (Exception ex) {
      logger.error("msg:" + message, ex);
    }
  }
}
