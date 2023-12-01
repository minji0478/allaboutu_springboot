package org.ict.allaboutu.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "board_category")
public class BoardCategory {

    @Id
    @Column(name = "category_num")
    private Long categoryNum;
    @Column(name = "category")
    private String category;

}
