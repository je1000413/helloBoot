package com.mice.backoffice.c2v_mice_backoffice_api.service.impl;

import com.mice.backoffice.c2v_mice_backoffice_api.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Set;

@Service

public class RedisServiceImpl implements RedisService {

    private static final String REDIS_SESSION_USERID_KEY_TEMPLATE = "z-users-hall-%s";
    private static final String REDIS_SESSION_NICK_NAME_KEY_TEMPLATE = "h-nicknames";
    private final RedisTemplate<String, Object> redisTemplateByStringSerializer;

    public RedisServiceImpl(@Qualifier("redisTemplateByStringSerializer") RedisTemplate<String, Object> redisTemplateByStringSerializer) {
        this.redisTemplateByStringSerializer = redisTemplateByStringSerializer;
    }

    @Override
    public Set<Object> getRedisAudiences(long sessionId) {
        String key = String.format(REDIS_SESSION_USERID_KEY_TEMPLATE, sessionId);
        ZSetOperations<String, Object> zSetOperations = redisTemplateByStringSerializer.opsForZSet();

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")));

        double start = Double.longBitsToDouble(timestamp.getTime());
        double end = Double.POSITIVE_INFINITY;

        return zSetOperations.rangeByScore(key, start, end);
    }

    @Override
    public String getRedisAudienceNickName(String accountId) {
        try {
            Object resultObj = redisTemplateByStringSerializer.opsForHash().get(REDIS_SESSION_NICK_NAME_KEY_TEMPLATE, accountId);

            if (Objects.isNull(resultObj))
                throw new Exception();

            String result = resultObj.toString();

            if (!StringUtils.hasText(result))
                throw new Exception();

            return result;
        }
        catch (Exception ex) {
            return String.format("UserNickName(%s)", accountId);
        }
    }

    @Override
    public String getRedisAudienceProfile(String profileKey) {
        Object resultObj = redisTemplateByStringSerializer.opsForValue().get(profileKey);
        return resultObj == null ? profileKey : resultObj.toString();
    }

    @Override
    public Set<Object> test() {
        getRedisAudienceNickName("4792");

        return null;
    }
}
