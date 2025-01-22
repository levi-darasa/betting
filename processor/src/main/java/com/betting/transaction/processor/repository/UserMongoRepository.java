package com.betting.transaction.processor.repository;

import com.betting.transaction.common.entity.mongo.UserMongoEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserMongoRepository extends ReactiveMongoRepository<UserMongoEntity, Integer>, UserMongoCustomRepository {
}
