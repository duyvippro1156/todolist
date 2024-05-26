package com.example.demo.controller;

import com.example.demo.dto.BoardDto;
import com.example.demo.model.Board;
import com.example.demo.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@CrossOrigin(origins = "*")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/")
    public ResponseEntity<List<Board>> getAllUserBoard() {
        return ResponseEntity.ok(boardService.getAllUserBoards());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> getTask(@PathVariable Long id) {
        Board board = boardService.getBoardWithId(id);
        if (board == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(board, HttpStatus.OK);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Board> createBoard(@RequestBody BoardDto boardDto) {
        return new ResponseEntity<>(boardService.createBoard(boardDto),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Board> updateBoard(@PathVariable Long id, @RequestBody BoardDto boardDto) {
        if (boardService.checkAuthor(id)){
            return new ResponseEntity<Board>(boardService.updateBoardWithId(boardDto,id), HttpStatus.OK);
        }else {
            return new ResponseEntity<Board>(boardService.getBoardWithId(id), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBoard(@PathVariable Long id){
        if (boardService.checkAuthor(id)){
            boardService.deleteBoardWithId(id);
            return ResponseEntity.ok(true);
        }else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}
