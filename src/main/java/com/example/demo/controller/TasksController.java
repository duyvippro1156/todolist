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
@RequestMapping("/api/board")
@CrossOrigin(origins = "*")
public class TasksController {
  /*
  .delete('/:boardId/columns/:columnId/tasks/:taskId', deleteTaskWithId)
  .post('/:boardId/columns/:columnId/tasks/:taskId/move/:toColumnId/index/:destinationIndex', moveTaskWithId)
  .post('/:boardId/columns/:columnId/index/:destinationIndex', moveColumnWithId);
   */
    @Autowired
    private TasksService tasksService;

//    @GetMapping("/")
//    public ResponseEntity<List<Task>> getAllTasks() {
//        return ResponseEntity.ok(tasksService.getAllTask());
//    }

    @GetMapping("/{boardId}/list/{listId}/tasks")
    public ResponseEntity<Task> getTask(@PathVariable Long boardId, @PathVariable Long listId) {
        Task tasks = tasksService.findTaskByListId(listId);
        if (tasks == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }   
    } 

    @PostMapping("/:boardId/task_list/:taskListId/tasks")
    public ResponseEntity<Task> createTask(@RequestBody TasksDto task, @PathVariable Long boardId, @PathVariable Long taskListId) {
        return new ResponseEntity<Task>(tasksService.createNewTask(task, boardId, taskListId),HttpStatus.CREATED);
    } 

    @PutMapping("/{boardId}/list/{listId}/tasks/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @PathVariable Long listId, @PathVariable Long boardId, @RequestBody TasksDto task) {
        if (tasksService.checkAuthor(id)){
            return new ResponseEntity<Task>(tasksService.updateTask(task, boardId, listId, id), HttpStatus.OK);
        }else {
//            return new ResponseEntity<TaskList>(tasksService.findTaskById(id), HttpStatus.BAD_REQUEST);
            return null;
        }
    } 
    @DeleteMapping("/{boardId}/list/{listId}/tasks/{id}")
    public ResponseEntity<Boolean> deleteTask(@PathVariable Long id, @PathVariable Long listId, @PathVariable Long boardId) {
        if (tasksService.checkAuthor(boardId)){
            tasksService.deleteTask(boardId, listId, id);
            return ResponseEntity.ok(true); 
        }else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST); 
        }
    } 

    @PostMapping("/search") 
    public ResponseEntity<List<Task>> seachByTaskName(@RequestParam String keyWord) {
        List<Task> listtTasks = tasksService.searchByTaskName(keyWord);
        if (listtTasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(listtTasks, HttpStatus.OK);
        }
    } 
}
    
