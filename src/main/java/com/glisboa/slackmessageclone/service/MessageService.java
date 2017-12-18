package com.glisboa.slackmessageclone.service;

import com.glisboa.slackmessageclone.entity.Message;
import com.glisboa.slackmessageclone.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Collection<Message> getAllMessages(){
        return messageRepository.getAllMessages();
    }

    public Message getMessageById(Integer id) {
        return messageRepository.getMessageById(id);
    }

    public Message createMessage(Message message) {
        messageRepository.createMessage(message);
        return message;
    }

    public Message updateMessage(Message message) {
        messageRepository.updateMessage(message);
        return message;
    }

    public Message deleteMessageById(Integer id) {
        messageRepository.deleteMessageById(id);
        return null;
    }
}

