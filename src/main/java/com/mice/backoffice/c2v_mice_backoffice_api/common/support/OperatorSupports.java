package com.mice.backoffice.c2v_mice_backoffice_api.common.support;

import lombok.Getter;

public interface OperatorSupports {


    @Getter
    enum OnOffType {

        Off(0),
        On(1)
        ;

        private final int cd;

        OnOffType(int cd) {
            this.cd = cd;
        }

        public int getValue() {
            return this.cd;
        }
    }

    @Getter
    enum CommandType {

        StreamingStart("StreamingStart"),
        StreamingEnd("StreamingEnd"),
        SessionChange("SessionChange"),
        SessionStatusChange("SessionStatusChange"),
        ProgramStatusChange("ProgramStatusChange"),
        MotionOnOff("MotionOnOff"),
        EffectOnOff("EffectOnOff"),
        UserKickOut("UserKickOut"),
        UserMicOnOff("UserMicOnOff"),
        QuestionStatusChange("QuestionStatusChange"),
        BlockUser("BlockUser"),
        TestMakeComplete("TestMakeComplete"),
        TestMakeFail("TestMakeFail"),
        EventDataChange("EventDataChange"),
        EventSpaceStateChange("EventSpaceStateChange")
        ;

        private final String cd;

        CommandType(String cd) {
            this.cd = cd;
        }

        public String getValue() {
            return this.cd;
        }
    }


}
