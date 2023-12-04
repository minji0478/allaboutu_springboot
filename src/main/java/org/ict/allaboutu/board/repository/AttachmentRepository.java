package org.ict.allaboutu.board.repository;

import org.ict.allaboutu.board.domain.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
