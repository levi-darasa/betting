package com.betting.transaction.processor.repository;

import com.betting.transaction.common.enums.UpdateBalanceAction;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface UserMongoCustomRepository {

    Mono<Void> updateBalance(Integer userId, BigDecimal balance, UpdateBalanceAction action);

}
