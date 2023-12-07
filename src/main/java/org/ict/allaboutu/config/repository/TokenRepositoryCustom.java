package org.ict.allaboutu.config.repository;

import org.ict.allaboutu.config.testModel.Tokens;

import java.util.List;

public interface TokenRepositoryCustom {
    List<Tokens> findAllValidTokenByUserId(String userName);
}
