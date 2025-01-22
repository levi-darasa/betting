package com.betting.transaction.common.entity.mongo;


import com.betting.transaction.common.constant.MongoConstant;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;

@Document(collection = MongoConstant.USER)
@Data
@Builder
public class UserMongoEntity {

    public static final String ID = "_id";
    public static final String BALANCE = "balance";

    @MongoId
    @Field(name = ID)
    private Integer id;

    @Field(name = BALANCE, targetType = FieldType.DECIMAL128)
    private BigDecimal balance;
}
