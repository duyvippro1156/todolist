package com.example.demo.controller;

import com.example.demo.dto.TaskListDto;
import com.example.demo.model.Board;
import com.example.demo.model.TaskList;
import com.example.demo.service.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
@CrossOrigin(origins = "*")
public class TaskListController {

    @Autowired
    private TaskListService taskListService;

    @PostMapping("/{boardId}/list")
    public ResponseEntity<TaskList> createTaskList(@RequestBody TaskListDto taskListDto, @PathVariable Long boardId){
        return new ResponseEntity<TaskList>(taskListService.addTaskListToBoard(taskListDto, boardId), HttpStatus.CREATED);
    }

    @PutMapping("/{boardId}/list/{listId}")
    public ResponseEntity<TaskList> updateTaskList(@RequestBody TaskListDto taskListDto, @PathVariable Long boardId, @PathVariable Long listId){
        if (taskListService.checkAuthor(boardId)){
            return new ResponseEntity<TaskList>(taskListService.editTaskListWithId(taskListDto,boardId,listId), HttpStatus.OK);
        }else {
            return new ResponseEntity<TaskList>(taskListService.getTaskListById(listId), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{boardId}/list/{listId}")
    public ResponseEntity<Boolean> deleteTaskList(@PathVariable Long boardId, @PathVariable Long listId){
        if (taskListService.checkAuthor(boardId)){
            taskListService.deleteTaskListWithId(boardId,listId);
            return ResponseEntity.ok(true);
        }else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

}
