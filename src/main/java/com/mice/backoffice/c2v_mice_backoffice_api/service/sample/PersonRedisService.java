package com.mice.backoffice.c2v_mice_backoffice_api.service.sample;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.redis.Person;

import java.util.List;

public interface PersonRedisService {

    List<Person> getById(String id);
    void addPerson(Person person);
}
