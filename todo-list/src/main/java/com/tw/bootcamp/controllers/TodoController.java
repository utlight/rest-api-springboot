package com.tw.bootcamp.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tw.bootcamp.entity.TodoItem;
import com.tw.bootcamp.exception.FailToFindItem;
import com.tw.bootcamp.todoDal.SimpleOpDAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todos")
public class TodoController {

    // integration
    private final SimpleOpDAL simpleOpDAL;

    @Autowired
    public TodoController(SimpleOpDAL simpleOpDAL) {
        this.simpleOpDAL = simpleOpDAL;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PATCH)
    @ResponseBody
    public String patch(@PathVariable("id")Integer id, @RequestParam(value = "title", required = false) String title, @RequestParam(value = "completed", required = false)Boolean completed,HttpServletResponse response) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> result = new HashMap<>();
        if("".equals(title) || title == null){
            result.put("message","bad title, it shouldn't be empty or null.");
            response.setStatus(400);
            return mapper.writeValueAsString(result);
        }

        TodoItem item = null;
        try{
            item = simpleOpDAL.patchTite(id, title);

        }catch (FailToFindItem ex){
            result.put("message", "can't find the todo item");
            response.setStatus(404);
            return mapper.writeValueAsString(result);
        }

        return mapper.writeValueAsString(item);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public HttpEntity<Map<String, String>> delete(@PathVariable("id")Integer id){
        HashMap<String, String> result = new HashMap<>();
        TodoItem item = null;
        try{
            simpleOpDAL.delete(id);
            result.put("message","successfully removed the item");
            return new ResponseEntity<>(result,HttpStatus.OK);

        }catch (FailToFindItem ex){
            result.put("message", "can't find the item by given id");
            return new ResponseEntity<>(result, HttpStatus.valueOf(404));
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<TodoItem> get(HttpServletResponse response){
        HashMap<String, String> result = new HashMap<>();
        List<TodoItem> list = simpleOpDAL.getAll();
        response.setStatus(200);
        return  list;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String post(@RequestParam(value = "title" , required = false) String title,HttpServletResponse response){
        HashMap<String, String> result = new HashMap<>();

        if("".equals(title) || title == null ){
            result.put("message","bad title, it shouldn't be empty or null.");
            response.setStatus(400);
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.writeValueAsString(result);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        TodoItem item = simpleOpDAL.createItem(title);

        String strItem = "";
        Gson gson = new Gson();
        strItem = gson.toJson(item);
        response.setStatus(200);
        return strItem;
    }

}
