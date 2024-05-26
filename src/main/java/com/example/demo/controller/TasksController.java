package com.example.demo.controller;

import com.example.demo.model.Task;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TasksDto;
import com.example.demo.model.TaskList;
import com.example.demo.service.TasksService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; 

import java.util.List;


@RestController
@RequestMapping("/api/boards")
@CrossOrigin(origins = "*")
public class TasksController {

    @Autowired
    private TasksService tasksService;

    @GetMapping("/{boardId}/columns/{listId}/tasks")
    public ResponseEntity<List<Task>> getTask(@PathVariable Long boardId, @PathVariable Long listId) {
        List<Task> tasks = tasksService.findTaskByListId(listId);
        if (tasks == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
        }   
    } 

    @PostMapping("/{boardId}/columns/{taskListId}/tasks")
    public ResponseEntity<Task> createTask(@RequestBody TasksDto task, @PathVariable Long boardId, @PathVariable Long taskListId) {
        return new ResponseEntity<Task>(tasksService.createNewTask(task, boardId, taskListId),HttpStatus.CREATED);
    } 

    @PutMapping("/{boardId}/columns/{listId}/tasks/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @PathVariable Long listId, @PathVariable Long boardId, @RequestBody TasksDto task) {
        if (tasksService.checkAuthor(id)){
            return new ResponseEntity<Task>(tasksService.updateTask(task, boardId, listId, id), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    } 
    @DeleteMapping("/{boardId}/columns/{listId}/tasks/{id}")
    public ResponseEntity<Boolean> deleteTask(@PathVariable Long id, @PathVariable Long listId, @PathVariable Long boardId) {
        if (tasksService.checkAuthor(boardId)){
            tasksService.deleteTask(boardId, listId, id);
            return ResponseEntity.ok(true); 
        }else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST); 
        }
    } 

    @PostMapping("/{boardId}/columns/{listId}/tasks/search")
    public ResponseEntity<List<Task>> seachByTaskName(@PathVariable Long listId, @PathVariable Long boardId, @RequestParam String keyWord) {
        List<Task> listTasks = tasksService.searchByTaskName(boardId, listId, keyWord);
        if (listTasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(listTasks, HttpStatus.OK);
        }
    } 
}
    
