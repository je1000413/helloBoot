package com.mice.backoffice.c2v_mice_backoffice_api.entity.redis;

import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash(value = "person", timeToLive = 240)
public class Person{
    @Id
    public String id;
    public String name;
    public Integer age;
    public LocalDateTime lastChngDtmd;

    public Person(String id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.lastChngDtmd = LocalDateTime.now();
    }
}