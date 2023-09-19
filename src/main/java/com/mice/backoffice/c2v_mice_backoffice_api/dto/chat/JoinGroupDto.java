package com.mice.backoffice.c2v_mice_backoffice_api.dto.chat;

import com.com2verse.platform.object.C2VResponse;
import com.com2verse.platform.object.ResponseCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinGroupDto {
    private int code;
    private String msg;
    private Object data;

    @Builder
    public JoinGroupDto(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static JoinGroupDto makeInstance(C2VResponse res) {
        return builder()
                .code(res.getCode())
                .msg(res.getMsg())
                .data(res.getData())
                .build();
    }
}
