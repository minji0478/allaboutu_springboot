package org.ict.allaboutu.member.domain;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profile_hashtag_link")
public class ProfileHashtagLink {

    @EmbeddedId
    private ProfileHashtagLinkPK id;
}
