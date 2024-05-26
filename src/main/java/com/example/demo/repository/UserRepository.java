package com.example.demo.repository;

import com.example.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findById(Long id);
    Optional<Users> findByUsernameOrEmail(String username, String email);
    Optional<Users> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    @Query(value = "SELECT u1.* FROM users u1 INNER JOIN user_task u2 ON u1.id = u2.user_id WHERE u2.task_id = ?1", nativeQuery = true)
	Users findUsersByTaskId(Long id);

    @Query(value = "SELECT u1.* FROM users u1 INNER JOIN boards b1 ON u1.id = b1.author_id WHERE b1.id = ?1", nativeQuery = true)
    Users findAuthorByBoardId(Long boardId);

    @Query(value = "SELECT u1.* FROM users u1 INNER JOIN boards b1 ON u1.id = b1.guess_id WHERE b1.id = ?1", nativeQuery = true)
    Users findGuessByBoardId(Long boardId);
}
