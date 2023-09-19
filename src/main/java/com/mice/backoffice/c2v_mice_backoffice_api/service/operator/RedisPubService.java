package com.mice.backoffice.c2v_mice_backoffice_api.service.operator;

import com.mice.backoffice.c2v_mice_backoffice_api.model.operator.PubSubPayload;
import jakarta.validation.Payload;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

public interface RedisPubService {

    String COMMAND_KEY = "mice:op:cmd:";

    String SESSION_KEY = "mice:op:session:";

    String COMMUNICATION_KEY = "mice:op:community:";

    void publish(Map<String, Object> value);

    void payloadPublish(PubSubPayload payload);

    void set(String key, Object value);

    void putAll(String key, Map<String, Object> value);

    boolean exists(String key);

    Map<Object, Object> get(String key);

    Object select();

    void delete(String key);

    void deleteForField(String key, String field);
}
