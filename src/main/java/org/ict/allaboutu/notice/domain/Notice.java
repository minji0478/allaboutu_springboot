package org.ict.allaboutu.notice.domain;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "notice")
public class Notice {
    @Id
    @Column(name="notice_num")
    private long noticeNum;
}
