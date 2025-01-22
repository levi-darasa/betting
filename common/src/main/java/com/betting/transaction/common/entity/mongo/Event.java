package com.betting.transaction.common.entity.mongo;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

import static com.betting.transaction.common.constant.MongoConstant.EVENT;

@Document(EVENT)
@Builder
@Data
public class Event {

    @MongoId
    private String id;

    private boolean executed;

    private Instant executedAt;
}
