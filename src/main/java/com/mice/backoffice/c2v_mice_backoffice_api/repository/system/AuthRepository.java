package com.mice.backoffice.c2v_mice_backoffice_api.repository.system;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.redis.Auth;
import org.springframework.data.repository.CrudRepository;

public interface AuthRepository extends CrudRepository<Auth, String> {
}
