package com.mice.backoffice.c2v_mice_backoffice_api.common.support;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

public interface EventSupports {
    enum PriceType {
        @SerializedName("1") FREE(1),
        @SerializedName("2") PAY(2),
        @SerializedName("3") MIX(3);

        @Getter
        @JsonValue
        private final int type;

        PriceType(int priceType) {
            this.type = priceType;
        }
    }

    enum TicketEndType {
        @SerializedName("1") SPECIFY_TIME(1),
        @SerializedName("2") END_EVENT(2),
        @SerializedName("3") FCFS_BASIS(3);

        @Getter
        @JsonValue
        private final int type;

        TicketEndType(int ticketEndType) {
            this.type = ticketEndType;
        }
    }

    enum CycleCode {
        @SerializedName("1") EVERYDAY(1),
        @SerializedName("2") WEEKLY(2),
        @SerializedName("3") MONTHLY(3),
        @SerializedName("4") EVERY_YEAR(4);

        @Getter
        @JsonValue
        private final int type;

        CycleCode(int cycleCode) {
            this.type = cycleCode;
        }

    }

    enum HostCode {
        @SerializedName("1") HOST(1),
        @SerializedName("2") SPONSOR(2),
        @SerializedName("3") AGENCY(3);

        @Getter
        @JsonValue
        private final int type;

        HostCode(int hostCode) {
            this.type = hostCode;
        }
    }

    enum EventStateCode {
        @SerializedName("0") ALL(0),
        @SerializedName("1") OPENED(1),
        @SerializedName("2") IN_PROGRESS(2),
        @SerializedName("3") END(3),
        @SerializedName("4") DELETE(4),
        @SerializedName("5") TEMP_SAVE(5);

        @Getter
        @JsonValue
        private final int type;
        EventStateCode(int eventStateCode) {
            this.type = eventStateCode;
        }
    }

    enum EventDisplayStateCode {
        @SerializedName("1") OPEN(1),
        @SerializedName("2") CLOSED(2);

        @Getter
        @JsonValue
        private final int type;

        EventDisplayStateCode(int eventDisplayStateCode) { this.type = eventDisplayStateCode; }
    }

    enum EventSellStateCode {
        @SerializedName("1") OPEN(1),
        @SerializedName("2") CLOSED(2);

        @Getter
        @JsonValue
        private final int type;

        EventSellStateCode(int eventSellStateCode) { this.type = eventSellStateCode; }
    }

    @Getter
    enum EventSearchDateType {
        NO_SELECTED(""),
        CREATE_DT("create_datetime"),
        EVENT_DT("event_datetime"),
        SELL_DT("sell_datetime"),
        DISPLAY_DT("display_datetime"),
        LOUNGE_DT("lounge_datetime");

        private final String _eventSearchDateType;

        EventSearchDateType(String eventSearchDateType) {
            this._eventSearchDateType = eventSearchDateType;
        }

        public String getValue(){
            if(this._eventSearchDateType == null)
                return "";
            else
                return this._eventSearchDateType.toString();
        }
    }

    @Getter
    enum EventSearchType {
        NO_SELECTED(""),
        EVENT_NAME("event_name"),
        EVENT_ID("event_id");

        private final String _eventSearchType;

        EventSearchType(String eventSearchType) {
            this._eventSearchType = eventSearchType;
        }

        public String getValue(){
            if(this._eventSearchType == null)
                return "";
            else
                return this._eventSearchType.toString();
        }
    }

    enum EventHostCode {
        @SerializedName("1") OPENED(1),
        @SerializedName("2") IN_PROGRESS(2),
        @SerializedName("3") END(3),
        @SerializedName("4") DELETE(4);

        @Getter
        @JsonValue
        private final int type;

        EventHostCode(int eventHostCode) {
            this.type = eventHostCode;
        }
    }

    enum EventSpaceStateCode {
        @SerializedName("1") UNOPENED(1),
        @SerializedName("2") OPEN(2),
        @SerializedName("3") CLOSED(3);

        @Getter
        @JsonValue
        private final int type;

        EventSpaceStateCode(int eventSpaceStateCode) { this.type = eventSpaceStateCode; }
    }
}
