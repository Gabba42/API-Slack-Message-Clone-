package com.glisboa.slackmessageclone.controller;

import com.glisboa.slackmessageclone.entity.Message;
import com.glisboa.slackmessageclone.service.MessageService;
import com.sun.org.apache.regexp.internal.RE;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static com.sun.javaws.JnlpxArgs.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(value=MessageController.class, secure=false)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;



    Message mockMessage = new Message(1, "Testing our message controller!", 1);
    Message mockMessage2 = new Message(2, "Another mock message!!", 2);
    ArrayList<Message> messages = new ArrayList<>();

    String exampleMessageJson = "{\"id\":1,\"messageBody\":\"Testing our message controller!\",\"userId\":1}";
    String examplePutMessageJson = "{\"id\":2,\"messageBody\":\"Another mock message!!\",\"userId\":2}";

    @Test
    public void getAllMessages() throws Exception {
        messages.add(mockMessage);
        messages.add(mockMessage2);

        Mockito.when(messageService.getAllMessages())
                .thenReturn(messages);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/messages")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        int status = result.getResponse().getStatus();

        //Verification
        assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);
        String expected = "[" + exampleMessageJson + ", " + examplePutMessageJson + "]";
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    public void getMessageById() throws Exception {
        //preparation
        Mockito.when(messageService.getMessageById(Mockito.anyInt()))
                .thenReturn(mockMessage);

        //execution
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/messages/1")
                .accept(MediaType.APPLICATION_JSON); //this line is possibly optional
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        int status = result.getResponse().getStatus();

        //Verification
        assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);
        String expected = exampleMessageJson;
        JSONAssert.assertEquals(expected, result.getResponse()
            .getContentAsString(), false);
    }

    @Test
    public void createMessage() throws Exception {
        //preparation
        Mockito.when(messageService.createMessage(Mockito.any(Message.class)))
                .thenReturn(mockMessage);

        //execution
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/messages")
                .accept(MediaType.APPLICATION_JSON)
                .content(exampleMessageJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        int status = result.getResponse().getStatus();

        //Verification
        assertEquals("Incorrect Response Status", HttpStatus.CREATED.value(), status);
        String expected = exampleMessageJson;
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    public void updateMessage() throws Exception {
        //preparation
        Mockito.when(messageService.updateMessage(Mockito.any(Message.class)))
                .thenReturn(mockMessage);

        //execution
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/messages")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"messageBody\":\"Testing our put method!\",\"userId\":1}")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        int status = result.getResponse().getStatus();

        //Verification
        assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);
    }

    @Test
    public void deleteMessageById() throws Exception {
        //preparation
        Mockito.when(messageService.deleteMessageById(Mockito.anyInt()))
                .thenReturn(mockMessage);

        //execution
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/messages")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"messageBody\":\"Testing our put method!\",\"userId\":1}")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        int status = result.getResponse().getStatus();

        //Verification
        assertEquals("Incorrect Response Status", HttpStatus.CREATED.value(), status);
        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .delete("/messages/1")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();
        int status2 = result2.getResponse().getStatus();

        //Verification
        assertEquals("Incorrect Response Status", HttpStatus.NOT_FOUND.value(), status2); //why not GONE?
    }

}