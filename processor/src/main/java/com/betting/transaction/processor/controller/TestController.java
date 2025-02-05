package com.betting.transaction.processor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class TestController {


    private final ReactiveRedisTemplate<Integer, BigDecimal> reactiveRedisTemplate;

    @GetMapping("")
    public String test() {
        return "Test controller";
    }

    @GetMapping("redis")
    public Mono<BigDecimal> testRedis() {
        return reactiveRedisTemplate.opsForValue().get(1);
    }

}
