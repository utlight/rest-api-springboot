package com.tw.bootcamp.todoDal;


import com.tw.bootcamp.entity.TodoItem;
import com.tw.bootcamp.exception.FailToFindItem;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//模拟数据操作
@Repository
public class SimpleOpDAL {

    private Map<Integer, TodoItem> data = new HashMap<>();
    private int index = 0;

    public SimpleOpDAL(){
        for (int i = 0; i < 10; i++) {
            TodoItem item = new TodoItem();
            item.setId(i+1);
            item.setTitle("title:" + (i + 1));
            item.setCompleted(false);
            item.setCreated_at(LocalDate.now().toString());
            item.setUpdated_at(LocalDate.now().toString());

            data.put(i+1,item);
            index ++;
        }
    }

    private Integer getNextId(){
        return index + 1;
    }

    public TodoItem patchTite(Integer id, String title) throws FailToFindItem {
        TodoItem todoItem = this.data.get(id);
        if(todoItem == null){
            throw  new FailToFindItem("can't find the item by given id");
        }

        todoItem.setTitle(title);
        data.put(id,todoItem);
        return todoItem;
    }

    public TodoItem patchCompleted(Integer id, Boolean completed) throws FailToFindItem{
        TodoItem todoItem = this.data.get(id);
        if(todoItem == null){
            throw  new FailToFindItem("can't find the item by given id");
        }

        todoItem.setCompleted(completed);
        data.put(id,todoItem);
        return todoItem;
    }

    public void delete(Integer id) throws FailToFindItem {
        TodoItem todoItem = this.data.get(id);
        if(todoItem == null){
            throw new FailToFindItem("can't find the item by given id");
        }

        this.data.remove(id);
    }

    public TodoItem createItem(String title){
        TodoItem item = new TodoItem();
        item.setCompleted(false);
        item.setTitle(title);
        item.setUpdated_at(LocalDate.now().toString());
        item.setCreated_at(LocalDate.now().toString());
        Integer nextId = this.getNextId();
        item.setId(nextId);

        data.put(nextId,item);
        return item;
    }

    public TodoItem getItemById(Integer id){
        return data.get(id);
    }

    public List<TodoItem> getAll(){
        return new ArrayList<>(data.values());
    }

}
