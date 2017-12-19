package com.glisboa.slackmessageclone.controller;


import com.glisboa.slackmessageclone.entity.Message;
import com.glisboa.slackmessageclone.service.MessageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/messages")
public class MessageController {
        private static Logger logger = Logger.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;


    @GetMapping
    public Collection<Message> getAllMessages() {

        return this.messageService.getAllMessages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int id) {

        Message message = messageService.getMessageById(id);

        if (message == null) {

            logger.debug("Message with " + id +"id is not found");
            return new ResponseEntity<Message>(HttpStatus.NOT_FOUND);
        }
        logger.debug("Message with " + id +"id is rendered");
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Void> createMessage(@RequestBody Message message) {

        this.messageService.createMessage(message);

        return new ResponseEntity<Void>(HttpStatus.CREATED);

    }

    @PutMapping
    public void updateMessage(@RequestBody Message message) {

        this.messageService.updateMessage(message);
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<Void> deleteMessageById(@PathVariable Integer id) {
        Message message = messageService.getMessageById(id);

        if (message == null) {

            logger.debug("Message with " + id +"id is not found");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } else {
            logger.debug("Message with " + id +"id is being deleted");
            messageService.deleteMessageById(id);

            return new ResponseEntity<Void>(HttpStatus.GONE);

        }

    }
}
