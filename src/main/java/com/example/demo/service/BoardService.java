package com.example.demo.service;

import com.example.demo.dto.BoardDto;
import com.example.demo.model.Board;

import java.util.List;

public interface BoardService {
    public boolean checkAuthor (Long boardId);
    public List<Board> getAllUserBoards();
    public Board createBoard(BoardDto boardDto);
    public Board getBoardWithId(Long boardId);
    public Board updateBoardWithId(BoardDto boardDto, Long boardId);
    public void deleteBoardWithId(Long boardId);
}
