package com.glisboa.slackmessageclone.repositories;

import com.glisboa.slackmessageclone.entity.Message;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MessageRepository {

    private static Map<Integer, Message> messages;

    static {
        messages = new HashMap<Integer, Message>() {

            {
                put(1, new Message(1, "Good morning!", 1));
                put(2, new Message(2, "Good afternoon!", 1));
                put(3, new Message(3, "Good night!", 1));
            }

        };
    }

    public Collection<Message> getAllMessages() {
        return this.messages.values();
    }

    public Message getMessageById(Integer id){
       return this.messages.get(id);
    }

    public Message deleteMessageById(Integer id) {
        this.messages.remove(id);
        return null;
    }

    public Message createMessage(Message message) {
       this.messages.put(message.getId(), message);
       return message;
    }

    public Message updateMessage(Message newMessage) {
        Message message = messages.get(newMessage.getId());
        message.setMessageBody(message.getMessageBody());
//        message.setDate(message.getDate());
        messages.put(newMessage.getId(),newMessage);
        return newMessage;
    }

}
