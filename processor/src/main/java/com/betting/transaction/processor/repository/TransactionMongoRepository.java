package com.betting.transaction.processor.repository;

import com.betting.transaction.common.entity.mongo.TransactionMongoEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TransactionMongoRepository extends ReactiveMongoRepository<TransactionMongoEntity, String> {
}
