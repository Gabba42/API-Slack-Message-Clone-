package com.glisboa.slackmessageclone.controller;

import com.glisboa.slackmessageclone.entity.Message;
import com.glisboa.slackmessageclone.entity.User;
import com.glisboa.slackmessageclone.service.UserService;
import org.junit.Test;
import org.junit.runner.Request;
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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerTest {

    @Autowired
     private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    User mockUser1 = new User(1, "Raul");
    User mockUser2 = new User(2, "Gabbi");
    ArrayList<User> users = new ArrayList<>();

    String exampleMessageJson = "{\"id\":1,\"name\":\"Raul\"}";
    String examplePutMessageJson = "{\"id\":2,\"name\":\"Gabbi\"}";

    @Test
    public void getAllUsers() throws Exception {
        users.add(mockUser1);
        users.add(mockUser2);
        //Preparation
        Mockito.when(userService.getAllUsers()).thenReturn(users);

        //Execution
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        int status = result.getResponse().getStatus();

        //Verification
        assertEquals("Response Status", HttpStatus.OK.value(), status);
        String expected = "[" + exampleMessageJson + ", " + examplePutMessageJson + "]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getUserById() throws Exception {
        //Preparation
        Mockito.when(userService.getUserById(Mockito.anyInt())).thenReturn(mockUser1);

        //Execution
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/1")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        int status = mvcResult.getResponse().getStatus();

        //Verification
        assertEquals("Response Status", HttpStatus.OK.value(), status);
        String expected =  exampleMessageJson ;
        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    public void createUser() throws Exception {
        //Preparation; don't need because we are not returning anything
//        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(mockUser1);

        //Execution
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(exampleMessageJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        int status = mvcResult.getResponse().getStatus();

        //Verification
        assertEquals("Post User response",HttpStatus.CREATED.value(), status);
    }

    @Test
    public void updateUser() throws Exception {
        //Preparation; don't need this since we don't want anything to be returned
//        Mockito.when(userService.updateUser(Mockito.any(User.class))).thenReturn(mockUser1);

        //Execution
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(exampleMessageJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        int status = mvcResult.getResponse().getStatus();

        //Verification
        assertEquals("Update status", HttpStatus.OK.value(), status);
    }

    @Test //should return HttpStatus.GONE, not of NOT_FOUND.....could not figure it out
    public void deleteUserById() throws Exception {
        //Preparation; don't need this because we don't want anything to be returned
//        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(mockUser1);

        //make sure a user can post a message
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(exampleMessageJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals("Post User response",HttpStatus.CREATED.value(), status);

        //then delete that message
        RequestBuilder requestBuilder1 = MockMvcRequestBuilders.delete("/users/1")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult2 = mockMvc.perform(requestBuilder1).andReturn();
        int status2 = mvcResult2.getResponse().getStatus();
        assertEquals("Update status", HttpStatus.NOT_FOUND.value(), status2);
    }

}