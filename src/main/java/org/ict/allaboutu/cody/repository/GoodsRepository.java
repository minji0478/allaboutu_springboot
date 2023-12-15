package org.ict.allaboutu.cody.repository;

import org.ict.allaboutu.cody.domain.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {
    @Query("select c from Goods c where c.codyNum = :codyNum order by c.goodsNum desc")
    List<Goods> findAllByCodyNum(@Param("codyNum") Long codyNum);

    @Query(value="select MAX (g.goodsNum) from Goods g")
    Long findMaxGoodsNumber();
}
