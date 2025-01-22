package com.betting.transaction.sender.services.grpc;

import com.betting.transaction.common.BalanceUpdateRequest;
import com.betting.transaction.common.BalanceUpdateResponse;
import com.betting.transaction.common.ReactorBalanceGrpc;
import com.betting.transaction.common.dto.BalanceUpdateRequestDto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BalanceClient {

    @GrpcClient("bet-service")
    ReactorBalanceGrpc.ReactorBalanceStub reactorBetStub;

    public Mono<BalanceUpdateResponse> balanceUpdate(BalanceUpdateRequestDto requestDto) {
        return reactorBetStub.balanceUpdate(BalanceUpdateRequest.newBuilder()
                .setUserId(requestDto.getUserId())
                .setAmount(requestDto.getStake().toString())
                .setAction(requestDto.getAction().name()).build());
    }

}
