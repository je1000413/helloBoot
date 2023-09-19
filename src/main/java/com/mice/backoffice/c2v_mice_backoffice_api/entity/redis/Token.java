package com.mice.backoffice.c2v_mice_backoffice_api.entity.redis;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash(value = "token")
public class Token {
    @Id
    public Long id;
    public String refreshToken;

    public Token(Long id, String refreshToken) {
        this.id = Long.valueOf(id);
        this.refreshToken = refreshToken;
    }
}