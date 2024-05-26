package com.example.demo.controller;

import com.example.demo.dto.TaskListDto;
import com.example.demo.model.TaskList;
import com.example.demo.service.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@CrossOrigin(origins = "*")
public class TaskListController {

    @Autowired
    private TaskListService taskListService;

    @GetMapping("/{boardId}/columns")
    public ResponseEntity<List<TaskList>> getTaskList(@PathVariable Long boardId){
        return new ResponseEntity<List<TaskList>>(taskListService.getTaskListByBoardId(boardId), HttpStatus.OK);
    }

    @PostMapping("/{boardId}/columns")
    public ResponseEntity<TaskList> createTaskList(@RequestBody TaskListDto taskListDto, @PathVariable Long boardId){
        return new ResponseEntity<TaskList>(taskListService.addTaskListToBoard(taskListDto, boardId), HttpStatus.CREATED);
    }

    @PutMapping("/{boardId}/columns/{listId}")
    public ResponseEntity<TaskList> updateTaskList(@RequestBody TaskListDto taskListDto, @PathVariable Long boardId, @PathVariable Long listId){
        if (taskListService.checkAuthor(boardId)){
            return new ResponseEntity<TaskList>(taskListService.editTaskListWithId(taskListDto,boardId,listId), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{boardId}/columns/{listId}")
    public ResponseEntity<Boolean> deleteTaskList(@PathVariable Long boardId, @PathVariable Long listId){
        if (taskListService.checkAuthor(boardId)){
            taskListService.deleteTaskListWithId(boardId,listId);
            return ResponseEntity.ok(true);
        }else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{boardId}/columns/search")
    public ResponseEntity<List<TaskList>> seachByTaskName(@PathVariable Long boardId, @RequestParam String keyWord) {
        List<TaskList> listTasks = taskListService.searchByListName(boardId, keyWord);
        if (listTasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(listTasks, HttpStatus.OK);
        }
    }

}
