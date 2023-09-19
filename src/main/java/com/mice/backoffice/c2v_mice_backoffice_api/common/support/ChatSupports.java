package com.mice.backoffice.c2v_mice_backoffice_api.common.support;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

public interface ChatSupports {
    enum AudienceSortType {
        @SerializedName("1") NAME(1),
        @SerializedName("2") NEW(2);

        @Getter
        @JsonValue
        private final int type;

        AudienceSortType(int visitorSortOrderType) { this.type = visitorSortOrderType; }
    }

    enum MessageSendType {
        @SerializedName("1") ALL(1),
        @SerializedName("2") LOCAL(2),
        @SerializedName("3") WHISPER(3);

        @Getter
        @JsonValue
        private final int type;

        MessageSendType(int messageType) { this.type = messageType; }
    }

    enum MessageType {
        @SerializedName("100") READ(100),
        @SerializedName("101") RECALL(101),
        @SerializedName("102") FORWARD(102),
        @SerializedName("103") GROUP_INVITE(103),
        @SerializedName("104") GROUP_EXIT(104),
        @SerializedName("105") SEND(105),
        @SerializedName("200") LOCAL_ENTER(200);

        @Getter
        @JsonValue
        private final int type;

        MessageType(int messageType) { this.type = messageType;}
    }

    enum MessageCommentType {
        @SerializedName("1000") NORMAL(1000),
        @SerializedName("1001") MENTION(1001),
        @SerializedName("1002") URL(1002),
        @SerializedName("1003") FILE_CONTENTS(1003),
        @SerializedName("1004") EMOTICON(1004),
        @SerializedName("1005") CUSTOM_DATA(1005);

        @Getter
        @JsonValue
        private final int type;

        MessageCommentType(int messageType) { this.type = messageType; }
    }
}
