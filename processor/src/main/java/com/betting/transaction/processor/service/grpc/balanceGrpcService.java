package com.betting.transaction.processor.service.grpc;

import com.betting.transaction.common.BalanceUpdateRequest;
import com.betting.transaction.common.BalanceUpdateResponse;
import com.betting.transaction.common.ReactorBalanceGrpc;
import com.betting.transaction.common.enums.UpdateBalanceAction;
import com.betting.transaction.processor.service.BalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@GrpcService
@Slf4j
@RequiredArgsConstructor
public class balanceGrpcService extends ReactorBalanceGrpc.BalanceImplBase {

    private final BalanceService balanceService;

    @Override
    public Mono<BalanceUpdateResponse> balanceUpdate(BalanceUpdateRequest request) {
        return balanceService.updateBalance(request.getUserId(), new BigDecimal(request.getAmount()), UpdateBalanceAction.valueOf(request.getAction()))
                .map(response -> BalanceUpdateResponse.newBuilder().setMessage(response).build());
    }
}
