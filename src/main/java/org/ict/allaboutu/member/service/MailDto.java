package org.ict.allaboutu.member.service;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "mail")
public class MailDto {
    @Id
    @Column(name = "mail_num")
    private Long mailNum;
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "token")
    private String token;
    @Column(name = "issued_at")
    private LocalDateTime issuedAt;
    @Column(name = "expired")
    private Long expired;
}
