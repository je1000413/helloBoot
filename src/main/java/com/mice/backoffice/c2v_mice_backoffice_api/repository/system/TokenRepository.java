package com.mice.backoffice.c2v_mice_backoffice_api.repository.system;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.redis.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Long> {


}
