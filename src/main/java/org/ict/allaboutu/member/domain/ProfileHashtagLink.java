package org.ict.allaboutu.member.domain;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "phl")
public class ProfileHashtagLink {
    @Id
    @Column(name = "HASHTAG_NUM")
    private Long hashtagNum;

    @Column(name = "USER_NUM")
    private Long userNum;
}
