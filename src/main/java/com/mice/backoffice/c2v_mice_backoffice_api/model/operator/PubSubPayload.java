package com.mice.backoffice.c2v_mice_backoffice_api.model.operator;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Map;

@Data
@Builder
public class PubSubPayload {    ;

    private String commandKey;

    private Status status;

    @Data
    @Builder
    public static class Status {

        @Builder.Default
        private String status = "send";

        @Builder.Default
        private String message = "";

        @Builder.Default
        private Integer retry = 0;

        @Builder.Default
        private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        private Object command;
    }

    public static Status toStatus(Map<Object, Object> map){
        /*
        Status status = new Status();
        status.setStatus(map.get("status").toString());
        status.setMessage(map.get("message").toString());
        status.setTimestamp(new Timestamp((Long)map.get("timestamp")));
        status.setCommand(map.get("command"));
        status.setRetry(((int)map.get("retry"))+1);
        return  status;
        */

        return  Status.builder()
                .status(map.get("status") == null ? "send" : map.get("status").toString())
                .message(map.get("message") == null ? "send" : map.get("message").toString())
                .timestamp(map.get("timestamp") == null ? new Timestamp(System.currentTimeMillis()) : new Timestamp(Long.parseLong(map.get("timestamp").toString())))
                .command(map.get("command"))
                .retry(map.get("retry") == null ? 1 : Integer.parseInt(map.get("retry").toString())+1)
                .build();
    }
}
