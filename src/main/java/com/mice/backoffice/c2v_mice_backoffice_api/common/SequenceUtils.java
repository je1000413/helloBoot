package com.mice.backoffice.c2v_mice_backoffice_api.common;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class SequenceUtils {
    private SequenceUtils() {}
    public static String getSequence() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    }

    public static Long getSequenceConvention(ConventionType type) {
        LocalDateTime now = LocalDateTime.now();
        String sequence = now.format(DateTimeFormatter.ofPattern("yyMMddHHmmssSSS"));

        return Long.parseLong(String.format("%s%s", type.type, sequence));
    }

    public enum ConventionType {
        EVENT(1),
        PROGRAM(2),
        SESSION(3);

        private final int type;

        ConventionType(int conventionType) { this.type = conventionType; }
    }
}
