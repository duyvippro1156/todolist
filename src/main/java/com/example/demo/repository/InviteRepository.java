package com.example.demo.repository;

import com.example.demo.model.Invite;
import org.springframework.stereotype.Repository;

@Repository
public interface InviteRepository {
    Invite getInviteIdForBoardWithId(Long boardId);
    void acceptBoardInvitationWithId(Long id);
    boolean areInvitesEnabledForBoardWithId(Long boardId);
    void removeInviteForBoardWithId(Long boardId);
}
