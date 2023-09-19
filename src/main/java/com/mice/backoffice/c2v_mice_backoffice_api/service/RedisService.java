package com.mice.backoffice.c2v_mice_backoffice_api.service;

import java.util.Set;

public interface RedisService {
    Set<Object> getRedisAudiences(long sessionId);

    Set<Object> test();
    String getRedisAudienceNickName(String accountId);
    String getRedisAudienceProfile(String profileKey);
}
