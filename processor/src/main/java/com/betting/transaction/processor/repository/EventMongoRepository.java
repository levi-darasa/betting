package com.betting.transaction.processor.repository;

import com.betting.transaction.common.entity.mongo.Event;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EventMongoRepository extends ReactiveMongoRepository<Event, String> {
}
