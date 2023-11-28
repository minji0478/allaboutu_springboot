package org.ict.allaboutu.chatbot.repository;

import org.ict.allaboutu.chatbot.domain.ChatLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatbotRepository extends JpaRepository<ChatLog, Long> {
}
