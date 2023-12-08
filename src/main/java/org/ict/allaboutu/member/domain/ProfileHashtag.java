package org.ict.allaboutu.member.domain;

import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profile_hashtag")
public class ProfileHashtag {
    @Id
    @Column(name = "HASHTAG_NUM")
    private Long hashtagNum;

    @Column(name = "CATEGORY_NUM")
    private Long categoryNum;

    @Column(name = "HASHTAG_NAME")
    private String hashtagName;
}
