package com.example.demo.service.impl;

import com.example.demo.dto.BoardDto;
import com.example.demo.model.Board;
import com.example.demo.model.Task;
import com.example.demo.model.Users;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Board> getAllUserBoards() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users userAuth = userRepository.findByEmail(auth.getName())
                .orElseThrow(() ->
                        new UsernameNotFoundException("not found  "+ auth.getName()));
        return boardRepository.getAllUserBoards(userAuth.getId());
    }

    @Override
    public Board createBoard(BoardDto boardDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() ->
                        new UsernameNotFoundException("not found  "+ auth.getName()));
        Board board = new Board();
        board.setName(boardDto.getName());
        board.setAuthor(user);
        return boardRepository.save(board);
    }

    @Override
    public Board getBoardWithId(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() ->
                new UsernameNotFoundException("not found"));
    }

    @Override
    public Board updateBoardWithId(BoardDto boardDto, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() ->
                new UsernameNotFoundException("not found"));
        board.setName(boardDto.getName());
        board.setId(boardId);
        if (checkAuthor(boardId)) {
            return boardRepository.save(board);
        } else {
            return boardRepository.findById(boardId).orElseThrow(() ->
                    new UsernameNotFoundException("not found"));
        }
    }

    @Override
    public void deleteBoardWithId(Long boardId) {
        if(checkAuthor(boardId)) {
            boardRepository.softDelete(boardId);
        }
    }

    public boolean checkAuthor(Long boardId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users users = userRepository.findAuthorByBoardId(boardId);
        Users userAuth = userRepository.findByEmail(auth.getName())
                .orElseThrow(() ->
                        new UsernameNotFoundException("not found  "+ auth.getName()));
        if(users.getId() == userAuth.getId()) {
            return true;
        } else {
            return false;
        }
    }
}
