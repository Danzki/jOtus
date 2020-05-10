package com.danzki.backend.mongo.handlers;


import com.danzki.core.model.User;
import com.danzki.core.service.DBServiceUser;
import com.danzki.messageSystem.Message;
import com.danzki.messageSystem.MessageType;
import com.danzki.messageSystem.RequestHandler;
import com.danzki.messageSystem.common.Serializers;
import org.bson.types.ObjectId;

import java.util.Optional;

public class GetUserDataRequestHandler implements RequestHandler {
    private final DBServiceUser dbServiceUser;

    public GetUserDataRequestHandler(DBServiceUser dbService) {
        this.dbServiceUser = dbService;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        ObjectId id = Serializers.deserialize(msg.getPayload(), ObjectId.class);
        User user = dbServiceUser.load(id);
        return Optional.of(new Message(msg.getTo(), msg.getFrom(), msg.getId(), MessageType.USER_DATA.getValue(), Serializers.serialize(user)));
    }
}
