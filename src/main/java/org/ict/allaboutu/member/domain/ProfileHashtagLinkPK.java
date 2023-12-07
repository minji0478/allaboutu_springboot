package org.ict.allaboutu.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ProfileHashtagLinkPK implements Serializable {

    @Column(name = "HASHTAG_NUM")
    private Long hashtagNum;

    @Column(name = "USER_NUM")
    private Long userNum;
}
