package com.betting.transaction.common.entity.mongo;

import com.betting.transaction.common.constant.MongoConstant;
import com.betting.transaction.common.enums.UpdateBalanceAction;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.time.Instant;

@Document(MongoConstant.TRANSACTION)
@Builder
@Data
public class TransactionMongoEntity {

    @MongoId
    private String id;

    private Integer userId;

    private UpdateBalanceAction action;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal amount;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal amountAfterUpdate;

    private Instant createdAt;
}
