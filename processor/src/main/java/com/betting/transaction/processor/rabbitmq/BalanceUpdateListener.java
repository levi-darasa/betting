package com.betting.transaction.processor.rabbitmq;

import com.betting.transaction.common.rabbitmq.BalanceUpdateMessage;
import com.betting.transaction.processor.service.BalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@RabbitListener(
        id = "BalanceUpdateListener",
        queues = "#{balanceUpdateQueue.name}",
        concurrency = "${rabbitmq.consumer.concurrency}",
        containerFactory = "jsaFactory"
)
public class BalanceUpdateListener {

    private final BalanceService balanceService;

    @RabbitHandler
    public void handleBalanceUpdate(BalanceUpdateMessage message) {
        balanceService.handleUpdateBalance(message).subscribe();
    }
}
