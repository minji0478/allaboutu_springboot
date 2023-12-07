package org.ict.allaboutu.config.repository;

import java.util.Optional;

import org.ict.allaboutu.config.testModel.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Tokens, Long>, TokenRepositoryCustom {
    Optional<Tokens> findByToken(String token);

}