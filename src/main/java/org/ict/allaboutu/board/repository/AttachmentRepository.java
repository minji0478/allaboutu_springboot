package org.ict.allaboutu.board.repository;

import org.ict.allaboutu.board.domain.Attachment;
import org.ict.allaboutu.board.domain.AttachmentPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, AttachmentPK> {

    @Query(value = "SELECT a FROM Attachment a WHERE a.id.boardNum = :boardNum")
    List<Attachment> findAllByBoardNum(Long boardNum);
}
