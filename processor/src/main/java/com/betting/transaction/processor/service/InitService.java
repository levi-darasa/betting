package com.betting.transaction.processor.service;


import com.betting.transaction.common.entity.mongo.Event;
import com.betting.transaction.common.entity.mongo.UserMongoEntity;
import com.betting.transaction.processor.repository.EventMongoRepository;
import com.betting.transaction.processor.repository.UserMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InitService {

    public static final String INIT_USER = "init_user";
    private final int NUMBER_USERS = 500;
    private final BigDecimal INIT_BALANCE = new BigDecimal("1000");
    private final UserMongoRepository userMongoRepository;
    private final EventMongoRepository eventMongoRepository;
    private final ReactiveRedisTemplate<Integer, BigDecimal> reactiveRedisTemplate;


    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        eventMongoRepository.findById(INIT_USER)
                .defaultIfEmpty(Event.builder().build())
                .subscribe(event -> {
                    if (event == null || !event.isExecuted()) {
                        long startTime = System.currentTimeMillis();
                        List<UserMongoEntity> users = new ArrayList<>();
                        for (int i = 1; i <= NUMBER_USERS; i++) {
                            users.add(UserMongoEntity.builder().id(i).balance(INIT_BALANCE).build());
                        }
                        userMongoRepository.saveAll(users)
                                .then(reactiveRedisTemplate.opsForValue().multiSet(users.stream().collect(Collectors.toMap(UserMongoEntity::getId, UserMongoEntity::getBalance))))
                                .then(eventMongoRepository.save(Event.builder().id(INIT_USER).executed(true).executedAt(Instant.now()).build()))
                                .subscribe(unused -> log.info("Init data completed in {} ms", System.currentTimeMillis() - startTime),
                                        error -> log.error(error.getMessage(), error)
                                );

                    }
                });
    }
}
