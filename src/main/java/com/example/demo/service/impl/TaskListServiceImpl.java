package com.example.demo.service.impl;

import com.example.demo.dto.TaskListDto;
import com.example.demo.model.Board;
import com.example.demo.model.TaskList;
import com.example.demo.model.Users;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.TaskListRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskListServiceImpl implements TaskListService {

    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<TaskList> getTaskListByBoardId(Long boardId) {
        return taskListRepository.findTaskListByBoard_Id(boardId);
    }

    @Override
    public TaskList addTaskListToBoard(TaskListDto taskListDto, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() ->
                new UsernameNotFoundException("not found "));
        TaskList taskList = new TaskList();
        taskList.setName(taskListDto.getName());
        taskList.setBoard(board);
        if (checkAuthor(boardId)){
            return taskListRepository.save(taskList);
        }else {
            return taskListRepository.findById(boardId).orElseThrow(() ->
                    new UsernameNotFoundException("not found "));
        }
    }

    @Override
    public void deleteTaskListWithId(Long boardId, Long id) {
        if(checkAuthor(boardId)){
            taskListRepository.deleteById(id);
        };
    }

    @Override
    public TaskList editTaskListWithId(TaskListDto taskListDto, Long boardId, Long id) {
        TaskList taskList = taskListRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("not found"));
        taskList.setName(taskListDto.getName());
        taskList.setId(id);
        if (checkAuthor(boardId)) {
            return taskListRepository.save(taskList);
        } else {
            return taskListRepository.findById(boardId).orElseThrow(() ->
                    new UsernameNotFoundException("not found"));
        }
    }

    @Override
    public void moveTaskListWithId(Long boardId, Long id) {

    }

    @Override
    public List<TaskList> searchByListName(Long boardId, String keyWord) {
        return taskListRepository.searchByListName(boardId, keyWord);
    }

    public boolean checkAuthor(Long boardId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users author = userRepository.findAuthorByBoardId(boardId);
        Users guess = userRepository.findGuessByBoardId(boardId);
        Users userAuth = userRepository.findByEmail(auth.getName())
                .orElseThrow(() ->
                        new UsernameNotFoundException("not found  "+ auth.getName()));
        if(author.getId() == userAuth.getId() || guess.getId() == userAuth.getId()) {
            return true;
        } else {
            return false;
        }
    }
}
