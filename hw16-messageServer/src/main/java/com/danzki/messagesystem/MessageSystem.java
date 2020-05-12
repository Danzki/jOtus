package com.danzki.messagesystem;

import com.danzki.messageSystem.Message;
import com.danzki.messageSystem.MsClient;

public interface MessageSystem {

    void addClient(MsClient msClient);

    void removeClient(String clientId);

    boolean newMessage(Message msg);

    void dispose() throws InterruptedException;

    void dispose(Runnable callback) throws InterruptedException;

    void start();

    int currentQueueSize();
}

