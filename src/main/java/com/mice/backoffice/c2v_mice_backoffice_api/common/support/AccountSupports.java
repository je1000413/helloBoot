package com.mice.backoffice.c2v_mice_backoffice_api.common.support;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

public interface AccountSupports {
    enum ClassCode {
        @SerializedName("1") NORMAL(1),
        @SerializedName("2") VIP(2),
        @SerializedName("3") VVIP(3),
        @SerializedName("4") BLOCK(4),
        @SerializedName("5") DROP_OUT(5);


        @Getter
        @JsonValue
        private final int code;

        ClassCode(int classCode) {
            this.code = classCode;
        }
    }
}
