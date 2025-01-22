package com.betting.transaction.common.rabbitmq;

import com.betting.transaction.common.enums.UpdateBalanceAction;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BalanceUpdateMessage {
    private Integer userId;
    private UpdateBalanceAction action;
    private BigDecimal amount;
    private BigDecimal amountAfterUpdate;
}
