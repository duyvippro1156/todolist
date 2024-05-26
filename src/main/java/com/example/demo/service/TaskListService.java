package com.example.demo.service;

import com.example.demo.dto.TaskListDto;
import com.example.demo.model.TaskList;

public interface TaskListService {
    public boolean checkAuthor (Long boardId);
    public TaskList addTaskListToBoard(TaskListDto taskListDto, Long boardId);
    public TaskList getTaskListById(Long id);
    public void deleteTaskListWithId(Long boardId, Long id);
    public TaskList editTaskListWithId(TaskListDto taskListDto, Long boardId, Long id);
    public void moveTaskListWithId(Long boardId, Long id);
}
