package com.mice.backoffice.c2v_mice_backoffice_api.repository.sample;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.redis.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRedisRepository extends CrudRepository<Person, String> {
}
