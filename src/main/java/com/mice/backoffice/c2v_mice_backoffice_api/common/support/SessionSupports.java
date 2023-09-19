package com.mice.backoffice.c2v_mice_backoffice_api.common.support;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

public interface SessionSupports {
    enum SessionType {
        @SerializedName("1") SELECT(1),
        @SerializedName("2") LIVE_STREAMING(2),
        @SerializedName("3") VOD_STREAMING(3),
        @SerializedName("4") VOD_PLAY(4),
        @SerializedName("5") IMAGE_COURTESY(5), //화상 제공형
        @SerializedName("6") VOICE_COURTESY(6), //보이스 제공형
        @SerializedName("7") YOUTUBE_PLAY(7);   //youtube play

        @Getter
        @JsonValue
        private final int type;

        SessionType(int sessionType) {
            this.type = sessionType;
        }
    }

    enum SessionEndType {
        @SerializedName("1") FORCED_EXIT(1),
        @SerializedName("2") CONTINUE(2);

        @Getter
        @JsonValue
        private final int type;

        SessionEndType(int sessionEndType) {
            this.type = sessionEndType;
        }
    }

    enum SessionStateCode {
        @SerializedName("1") READY(1),
        @SerializedName("2") IN_LECTURE(2),
        @SerializedName("3") REST(3),
        @SerializedName("4") QUESTION_AND_ANSWER(4),
        @SerializedName("5") ENTER(5),
        @SerializedName("6") END(6),
        @SerializedName("7") DELETE(7);

        @Getter
        @JsonValue
        private final int type;

        SessionStateCode(int sessionStateCode) {
            this.type = sessionStateCode;
        }
    }

    enum SessionDisplayStateCode {
        @SerializedName("1") USE(1),
        @SerializedName("2") NONE_USE(2),
        @SerializedName("3") DELETE(3);

        @Getter
        @JsonValue
        private final int type;

        SessionDisplayStateCode(int sessionDisplayStateCode) { this.type = sessionDisplayStateCode; }
    }

    enum SessionOnlineCode {
        @SerializedName("1") ONLINE_AND_OFFLINE(1),
        @SerializedName("2") ONLINE_ONLY(2);

        @Getter
        @JsonValue
        private final int type;

        SessionOnlineCode(int sessionOnlineCode) { this.type = sessionOnlineCode; }
    }

    enum StaffType {
        @SerializedName("1") SPEAKER(1),
        @SerializedName("2") MODERATOR(2);

        @Getter
        @JsonValue
        private final int type;

        StaffType(int staffType){
            this.type = staffType;
        }
    }

    enum TicketOnlineCode {
        @SerializedName("1") ONLINE(1),
        @SerializedName("2") OFFLINE(2);

        @Getter
        @JsonValue
        private final int type;

        TicketOnlineCode(int ticketOnlineCode) {
            this.type = ticketOnlineCode;
        }
    }

    enum TicketStateCode {
        @SerializedName("1") NORMAL(1);

        @Getter
        @JsonValue
        private final int type;

        TicketStateCode(int ticketStateCode) { this.type = ticketStateCode; }
    }

    enum SessionQuestionStateCode {
        @SerializedName("1") BEFORE_ANSWER(1),
        @SerializedName("2") ANSWERING(2),
        @SerializedName("3") ANSWER_COMPLETE(3);

        @Getter
        @JsonValue
        private final int type;

        SessionQuestionStateCode(int sessionQuestionStateCode) { this.type = sessionQuestionStateCode; }
    }

    enum SessionQuestionListOrder {
        @SerializedName("1") LIKE(1),
        @SerializedName("2") VIEW(2),
        @SerializedName("3") NAME(3);

        @Getter
        @JsonValue
        private final int type;

        SessionQuestionListOrder(int sessionQuestionSearchOrder) { this.type = sessionQuestionSearchOrder; }
    }

    @Getter
    enum SessionSearchType {
        NO_SELECTED(""),
        PROGRAM_NM("program_name"),
        PROGRAM_ID("program_id"),
        SESSION_NM("session_name"),
        SESSION_ID("session_id");
        private final String _sessionSearchType;

        SessionSearchType(String sessionSearchType) {
            this._sessionSearchType = sessionSearchType;
        }

        public String getValue(){
            if(this._sessionSearchType == null)
                return "";
            else
                return this._sessionSearchType.toString();
        }
    }
}
