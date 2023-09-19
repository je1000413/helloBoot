package com.mice.backoffice.c2v_mice_backoffice_api.common.support;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

public interface ProgramSupports {
    enum ProgramType {
        @SerializedName("1") FAIR(1),
        @SerializedName("2") EXHIBITION(2),
        @SerializedName("3") CONFERENCE(3),
        @SerializedName("4") SPECIAL_MEETUP(4),
        @SerializedName("5") OTHER_EVENT(5);

        @Getter
        @JsonValue
        private final int type;

        ProgramType(int programType) {
            this.type = programType;
        }
    }

    enum SwitchType {
        @SerializedName("1") AUTOMATIC(1),
        @SerializedName("2") MANUAL(2);

        @Getter
        @JsonValue
        private final int type;

        SwitchType(int switchType) {
            this.type = switchType;
        }
    }

    @Getter
    enum ProgramSearchDateType {
        NO_SELECTED(""),
        CREATE_DT("create_datetime"),
        UPDATE_DT("event_datetime"),
        PROGRAM_DT("program_datetime");
        private final String _programSearchDateType;

        ProgramSearchDateType(String programSearchDateType) {
            this._programSearchDateType = programSearchDateType;
        }

        public String getValue(){
            if(this._programSearchDateType == null)
                return "";
            else
                return this._programSearchDateType.toString();
        }
    }

    @Getter
    enum ProgramSearchType {
        NO_SELECTED(""),
        PROGRAM_NM("program_name"),
        PROGRAM_ID("program_id"),
        EVENT_NM("event_name"),
        EVENT_ID("event_id");
        private final String _programSearchType;

        ProgramSearchType(String programSearchType) {
            this._programSearchType = programSearchType;
        }

        public String getValue(){
            if(this._programSearchType == null)
                return "";
            else
                return this._programSearchType.toString();
        }
    }
}
