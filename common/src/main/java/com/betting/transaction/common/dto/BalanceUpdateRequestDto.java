package com.betting.transaction.common.dto;

import com.betting.transaction.common.enums.UpdateBalanceAction;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceUpdateRequestDto {
    private Integer userId;
    private BigDecimal stake;
    private UpdateBalanceAction action;
}
