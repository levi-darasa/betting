package com.betting.transaction.processor.service;

import com.betting.transaction.common.entity.mongo.TransactionMongoEntity;
import com.betting.transaction.common.enums.UpdateBalanceAction;
import com.betting.transaction.common.rabbitmq.BalanceUpdateMessage;
import com.betting.transaction.processor.repository.TransactionMongoRepository;
import com.betting.transaction.processor.repository.UserMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceService {

    @Value("${rabbit.exchanges.balance-update}")
    private String balanceUpdateExchange;

    @Value("${rabbit.keys.balance-update}")
    private String balanceUpdateKey;


    private final ReactiveRedisTemplate<Integer, BigDecimal> reactiveRedisTemplate;
    private final TransactionMongoRepository transactionMongoRepository;
    private final UserMongoRepository userMongoRepository;
    private final RabbitTemplate rabbitMqTemplate;

    @Qualifier("decreaseUserBalanceScript")
    private final RedisScript<BigDecimal> decreaseUserBalanceScript;

    @Qualifier("increaseUserBalanceScript")
    private final RedisScript<BigDecimal> increaseUserBalanceScript;


    public Mono<String> updateBalance(Integer userId, BigDecimal amount, UpdateBalanceAction action) {
        return reactiveRedisTemplate.execute(
                        action == UpdateBalanceAction.DEBIT ? decreaseUserBalanceScript : increaseUserBalanceScript,
                        List.of(userId),
                        List.of(amount))
                .next()
                .map(result ->
                        switch (result.toString()) {
                            case "-1" -> "User not found";
                            case "-2" -> "Insufficient balance";
                            default -> {
                                BalanceUpdateMessage message = BalanceUpdateMessage.builder()
                                        .userId(userId)
                                        .amount(amount)
                                        .action(action)
                                        .amountAfterUpdate(result)
                                        .build();
//                                rabbitMqTemplate.convertAndSend(balanceUpdateExchange, balanceUpdateKey, message);
                                yield "Balance updated successfully: " + result;
                            }
                        })
                .doOnError(throwable -> log.error(throwable.getMessage(), throwable));
    }

    @Transactional
    public Mono<Void> handleUpdateBalance(BalanceUpdateMessage message) {

        // Build the Transaction entity
        TransactionMongoEntity transaction = TransactionMongoEntity.builder()
                .userId(message.getUserId())
                .amount(message.getAmount())
                .action(message.getAction())
                .amountAfterUpdate(message.getAmountAfterUpdate())
                .createdAt(Instant.now())
                .build();

        // Save the transaction and update the user's balance reactively
        return transactionMongoRepository.save(transaction)  // Save transaction
                .then(userMongoRepository.updateBalance(message.getUserId(), message.getAmount(), message.getAction()))
                .doOnError(throwable -> log.error("Error occurred: {}", throwable.getMessage(), throwable));
    }

}
