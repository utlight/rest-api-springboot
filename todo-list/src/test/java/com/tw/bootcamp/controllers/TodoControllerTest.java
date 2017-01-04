package com.tw.bootcamp.controllers;

import com.tw.bootcamp.todoDal.SimpleOpDAL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private SimpleOpDAL simpleOpDAL;

    @Test
    public void should_post_return_success() throws Exception {
        String requestBody = "{title:123}";
        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON).param("title","111")//content(requestBody)
                .accept(MediaType.APPLICATION_JSON))
                // .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.message").value("bad title, it shouldn't be empty or null."));
    }

    @Test
    public void should_post_return_error() throws Exception {
        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").value("bad title, it shouldn't be empty or null."));
    }

    @Test
    public void should_get_list() throws Exception {
        mockMvc.perform(get("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void should_delete_successful() throws Exception{
        mockMvc.perform(delete("/todos/1")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("successfully removed the item"));
    }

    @Test
    public void should_delete_failed() throws Exception{
        mockMvc.perform(delete("/todos/111111")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("can't find the item by given id"));
    }

    @Test
    public void should_patch_title_success() throws Exception{
        mockMvc.perform(patch("/todos/1")
                .param("title","testTtitle")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void should_patch_title_failofnull() throws Exception{
        mockMvc.perform(patch("/todos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("bad title, it shouldn't be empty or null."));

    }

    @Test
    public void should_patch_title_failofid() throws Exception{
        mockMvc.perform(patch("/todos/111")
                .param("title","true")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("can't find the todo item"));
    }

}
