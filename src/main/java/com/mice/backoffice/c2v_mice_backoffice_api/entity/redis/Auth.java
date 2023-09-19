package com.mice.backoffice.c2v_mice_backoffice_api.entity.redis;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash(value = "auth")
public class Auth {
    @Id
    public String id;
    public int authNumber;

    public Auth(String id, int authNumber) {
        this.id = id;
        this.authNumber = authNumber;
    }
}