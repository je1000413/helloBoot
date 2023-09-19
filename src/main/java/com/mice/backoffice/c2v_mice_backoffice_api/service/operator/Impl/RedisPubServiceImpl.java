package com.mice.backoffice.c2v_mice_backoffice_api.service.operator.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mice.backoffice.c2v_mice_backoffice_api.model.operator.PubSubPayload;
import com.mice.backoffice.c2v_mice_backoffice_api.service.operator.RedisPubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisPubServiceImpl implements RedisPubService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final String subscribeKey = "w_to_mice_message";
    private final String prefixCommandKey = RedisPubService.COMMAND_KEY;

    @Value("${spring.data.redis.pub-sub.expire}")
    private Integer expire;

    @Value("${spring.data.redis.pub-sub.is-expire}")
    private Boolean isExpire;

    public RedisPubServiceImpl(@Qualifier("redisTemplateByJsonSerializer") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void publish(Map<String, Object> value) {
        var payload = PubSubPayload.builder().commandKey(value.get("commandKey").toString()).status(PubSubPayload.Status.builder().command(value).build()).build();
        payloadPublish(payload);
    }

    @Override
    public void payloadPublish(PubSubPayload payload) {
        log.info("publish - {}", payload);
        redisTemplate.opsForHash().putAll(payload.getCommandKey(), new ObjectMapper().convertValue(payload.getStatus(), Map.class));
        if(isExpire)
            redisTemplate.expire(payload.getCommandKey(), expire, TimeUnit.SECONDS);
        redisTemplate.convertAndSend(subscribeKey, payload.getStatus().getCommand());
    }

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void putAll(String key, Map<String, Object> value) {
        redisTemplate.opsForHash().putAll(key, value);
    }

    @Override
    public Map<Object, Object> get(String key) {
        try {
            redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(new ObjectMapper()));
            return redisTemplate.opsForHash().entries(key);
        } catch (Exception ex) {
            redisTemplate.setHashValueSerializer(new GenericToStringSerializer<>(Object.class));
            return redisTemplate.opsForHash().entries(key);
        }  finally {
            redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        }
    }

    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public Object select() {
        ScanOptions scanOptions = ScanOptions.scanOptions().match(prefixCommandKey + "*").count(500).build();
        DefaultStringRedisConnection connection = new DefaultStringRedisConnection(Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection());
        Cursor<byte[]> keys = connection.scan(scanOptions);
        List<String> result = new ArrayList<>();
        while(keys.hasNext()){
            result.add(new String(keys.next()));
        }
        return result;
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void deleteForField(String key, String field) {
        redisTemplate.opsForHash().delete(key, field);
    }
}
