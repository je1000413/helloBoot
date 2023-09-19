package com.mice.backoffice.c2v_mice_backoffice_api.common.support;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

public interface AdminSupports {

    @Getter
    enum AdminStateCode {

        NORMAL(1),
        NOT_USED(2),
        NOT_NORMAL(3);

        private final int _adminStateCode;

        AdminStateCode(int adminStateCode) {
            this._adminStateCode = adminStateCode;
        }

        public int getValue() {
            if(this._adminStateCode < 1)
                return 1;
            else
                return this._adminStateCode;
        }
    }

    enum AdminRoleCode {
        SUPER_ADMIN(1),
        NORMAL_ADMIN(2),
        SUPER_ADMIN_FOR_DOMAIN(3),
        NORMAL_ADMIN_FOR_DOMAIN(4),
        OPERATOR(5)
        ;

        private final int _adminRoleCode;

        AdminRoleCode(int adminRoleCode) {
            this._adminRoleCode = adminRoleCode;
        }

        public int getValue() {
            if(this._adminRoleCode < 1)
                return 0;
            else
                return this._adminRoleCode;
        }
    }

    enum AdminAuthorityCode {
        READ_WRITE(1),
        ONLY_READ(2)
        ;

        private final int _code;

        AdminAuthorityCode(int code) {
            this._code = code;
        }

        public int getValue() {
            if(this._code < 1)
                return 0;
            else
                return this._code;
        }
    }

    enum AdminGroupStateCode {
        NORMAL(1),
        NOT_USED(2)
        ;

        private final int _code;

        AdminGroupStateCode(int code) {
            this._code = code;
        }

        public int getValue() {
            if(this._code < 1)
                return 0;
            else
                return this._code;
        }
    }

    @Getter
    enum AdminSearchType {
        NO_SELECTED(""),
        DOMAIN_NAME("domain_name"),
        DOMAIN_ID("domain_id"),
        ACCOUNT_ID("account_id");


        private final String _adminSearchType;

        AdminSearchType(String adminSearchType) {
            this._adminSearchType = adminSearchType;
        }

        public String getValue(){
            if(this._adminSearchType == null)
                return "";
            else
                return this._adminSearchType.toString();
        }

    }

    enum AdminLogMappingType {
        @SerializedName("1000") EVENT(1000);

        @Getter
        @JsonValue
        private final int type;

        AdminLogMappingType(int adminLogMappingType) { this.type = adminLogMappingType; }
    }

    enum AdminLogActionCode {
        @SerializedName("1000") EVENT_SELL_EMERGENCY_STATE(1000),
        @SerializedName("1001") EVENT_DISPLAY_STATE(1001);

        @Getter
        @JsonValue
        private final int type;

        AdminLogActionCode(int adminLogActionCode) { this.type = adminLogActionCode; }
    }

    @Getter
    enum DomainSearchType {
        NO_SELECTED(""),
        DOMAIN_NAME("domain_name"),
        DOMAIN_ID("domain_id");

        private final String _domainSearchType;

        DomainSearchType(String domainSearchType) {
            this._domainSearchType = domainSearchType;
        }

        public String getValue(){
            if(this._domainSearchType == null)
                return "";
            else
                return this._domainSearchType.toString();
        }
    }

    @Getter
    enum GroupSearchType {
        NO_SELECTED(""),
        GROUP_NAME("group_name"),
        GROUP_ID("group_id");

        private final String _groupSearchType;

        GroupSearchType(String groupSearchType) {
            this._groupSearchType = groupSearchType;
        }

        public String getValue(){
            if(this._groupSearchType == null)
                return "";
            else
                return this._groupSearchType.toString();
        }
    }
}
