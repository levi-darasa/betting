package com.betting.transaction.processor.repository;

import com.betting.transaction.common.entity.mongo.UserMongoEntity;
import com.betting.transaction.common.enums.UpdateBalanceAction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class UserMongoCustomRepositoryImpl implements UserMongoCustomRepository {

    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<Void> updateBalance(Integer userId, BigDecimal balance, UpdateBalanceAction action) {
        Query query = new Query().addCriteria(Criteria.where(UserMongoEntity.ID).is(userId));
        Update update = new Update();
        update.setOnInsert(UserMongoEntity.ID, userId);

        switch (action) {
            case DEBIT -> update.inc(UserMongoEntity.BALANCE, balance.negate());
            case CREDIT -> update.inc(UserMongoEntity.BALANCE, balance);
        }

        return mongoTemplate.upsert(query, update, UserMongoEntity.class).then();
    }


}
