package org.ict.allaboutu.chatbot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chatbot_log")
public class ChatLog {
    @Id
    @Column(name = "chatlog_num")
    private long chatlogNum;
    @Column(name = "user_num")
    private long userNum;
    @Column(name = "query")
    private String query;
    @Column(name = "answer")
    private String answer;
    @Column(name = "generated_img")
    private String generatedImg;
    @Column(name = "prev_chatlog_num")
    private long prevChatlogNum;
    @Column(name = "create_date")
    private LocalDateTime createDate;
}
