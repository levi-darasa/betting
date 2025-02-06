package com.betting.transaction.sender.controller;


import com.betting.transaction.common.BalanceUpdateResponse;
import com.betting.transaction.common.dto.BalanceUpdateRequestDto;
import com.betting.transaction.sender.services.grpc.BalanceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/betting")
@RequiredArgsConstructor
public class BetController {

    private final BalanceClient balanceClient;

    @PostMapping("")
    public Mono<BalanceUpdateResponse> updateBalance(@RequestBody BalanceUpdateRequestDto requestDto) {
//        return balanceClient.balanceUpdate(requestDto);
        return Mono.empty();
    }

    @GetMapping("")
    public Mono<String> getBalance() {
        return Mono.empty();
    }
}
