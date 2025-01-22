package com.betting.transaction.processor.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;

import java.io.IOException;
import java.math.BigDecimal;

@Configuration
public class DragonflyConfig {

    @Bean
    public ReactiveRedisTemplate<Integer, BigDecimal> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Integer> keySerializer = new Jackson2JsonRedisSerializer<>(Integer.class);
        Jackson2JsonRedisSerializer<BigDecimal> valueSerializer = new Jackson2JsonRedisSerializer<>(BigDecimal.class);
        RedisSerializationContext.RedisSerializationContextBuilder<Integer, BigDecimal> builder =
                RedisSerializationContext.newSerializationContext(keySerializer);
        RedisSerializationContext<Integer, BigDecimal> context = builder.value(valueSerializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

    @Bean
    @Qualifier("decreaseUserBalanceScript")
    public RedisScript<BigDecimal> decreaseUserBalanceScript() throws IOException {
        ScriptSource scriptSource = new ResourceScriptSource(new ClassPathResource("META-INF/scripts/decreaseUserBalance.lua"));
        return RedisScript.of(scriptSource.getScriptAsString(), BigDecimal.class);
    }

    @Bean
    @Qualifier("increaseUserBalanceScript")
    public RedisScript<BigDecimal> increaseUserBalanceScript() throws IOException {
        ScriptSource scriptSource = new ResourceScriptSource(new ClassPathResource("META-INF/scripts/increaseUserBalance.lua"));
        return RedisScript.of(scriptSource.getScriptAsString(), BigDecimal.class);
    }
}
