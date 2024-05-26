package com.example.demo.repository;

import com.example.demo.model.Board;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query(value = "SELECT b1.* FROM boards b1 WHERE b1.author_id=? OR b1.guess_id=? AND b1.is_delete=0", nativeQuery = true)
    List<Board> getAllUserBoards(Long userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE boards b1 SET b1.is_delete = 1 WHERE b1.id = ?1", nativeQuery = true)
    void softDelete (Long boardId);

}
