package org.ict.allaboutu.admin.domain;

import lombok.*;

import jakarta.persistence.*;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PROFILE_HASHTAG")
public class ProfileHashtag {

    @Id
    @Column(name = "HASHTAG_NUM")
    private Long hashtagNum;

    @Column(name = "CATEGORY_NUM")
    private Long categoryNum;

    @Column(name = "HASHTAG_NAME")
    private String hashtagName;
}
