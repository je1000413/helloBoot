package com.mice.backoffice.c2v_mice_backoffice_api.common.support;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

public interface Supports {
    enum SurveyCode {
        @SerializedName("1") SURVEY(1),
        @SerializedName("2") SATISFACTION(2);

        @Getter
        @JsonValue
        private final int type;

        SurveyCode(int type) {
            this.type = type;
        }
    }

    enum SurveyStateCode {
        @SerializedName("1") USE(1),
        @SerializedName("2") NONE_USE(2),
        @SerializedName("3") DELETE(3);

        @Getter
        @JsonValue
        private final int type;

        SurveyStateCode(int surveyStateCode) { this.type = surveyStateCode;}
    }

    enum SurveyMappingType {
        @SerializedName("1") EVENT(1),
        @SerializedName("2") PROGRAM(2),
        @SerializedName("3") SESSION(3);

        @Getter
        @JsonValue
        private final int type;

        SurveyMappingType(int surveyMappingType) { this.type = surveyMappingType;}
    }

    enum LanguageType {
        @SerializedName("ko_kr") KO_KR(0),
        @SerializedName("en_us") EN_US(1);
        //@SerializedName("ja_jp") JA_JP(3);

        @Getter
        private final int type;

        LanguageType(int languageType) {
            this.type = languageType;
        }

        int type() {
            return type;
        }

        @JsonValue
        String getLanguageType() {
            return this.name().toLowerCase();
        }
    }
    enum FunctionType {
        @SerializedName("1") EVENT(1),
        @SerializedName("2") PROGRAM(2),
        @SerializedName("3") SESSION(3),
        @SerializedName("4") LOUNGE(4);
        @Getter
        @JsonValue
        private final int type;

        FunctionType(int functionType) {
            this.type = functionType;
        }

        public static FunctionType fromValue(int value) {
            for (FunctionType functionType : FunctionType.values()) {
                if (functionType.getType() == value) {
                    return functionType;
                }
            }
            throw new IllegalArgumentException("Invalid FunctionType value: " + value);
        }
    }
    enum DisplayCode {
        @SerializedName("10001") HOLOGRAM(10001),
        @SerializedName("10002") SCREEN(10002),
        @SerializedName("10003") EVENT_PAMPHLET(10003),
        @SerializedName("10101") COVER_IMAGE(10101),
        @SerializedName("10102") LECTURE_SCREEN(10102),
        @SerializedName("10103") PROMOTION_SCREEN(10103),
        @SerializedName("10104") POPUP_BANNER(10104),
        @SerializedName("10105") WALL_BANNER(10105),
        @SerializedName("10106") ATTACHMENT(10106),
        @SerializedName("10107") DETAIL_PAGE(10107),
        @SerializedName("10108") MOBILE_BOTTOM_BANNER(10108),
        @SerializedName("10201") LEAFLET(10201),
        @SerializedName("10202") KIOSK(10202),
        @SerializedName("10203") SPECIAL_KIOSK(10203),
        @SerializedName("10204") AD_SCREEN(10204),
        @SerializedName("10205") BANNER(10205),
        @SerializedName("10301") PROGRAM_COVER_IMAGE(10301),
        @SerializedName("10401") STAFF_IMAGE(10401),
        @SerializedName("10999") ETC(10999),

        @SerializedName("20901") TMP_SPEAKER_PROFILE(20901),

        @SerializedName("20902") TMP_LECTURE_INFO(20902),

        @SerializedName("20903") TMP_KEYWORD_SCREEN(20903)
        ;



        @Getter
        @JsonValue
        private final int type;

        DisplayCode(int displayCode) {
            this.type = displayCode;
        }

        public static DisplayCode fromValue(int value) {
            for (DisplayCode displayCode : DisplayCode.values()) {
                if (displayCode.getType() == value) {
                    return displayCode;
                }
            }
            throw new IllegalArgumentException("Invalid FunctionType value: " + value);
        }
    }

    enum DisplayType {
        @SerializedName("20001") PDF(20001),
        @SerializedName("20002") URL(20002),
        @SerializedName("20003") IMAGE(20003),
        @SerializedName("20004") HTML(20004),
        @SerializedName("20005") IMAGE_LINK(20005),
        @SerializedName("20006") ATTACHMENT(20006),
        @SerializedName("20007") NONE(20007),
        @SerializedName("20008") YOUTUBE_VIDEO(20008);

        @Getter
        @JsonValue
        private final int type;

        DisplayType(int displayType) {
            this.type = displayType;
        }
    }

    enum ScreenStateCode {
        @SerializedName("1") USE(1),
        @SerializedName("2") NONE_USE(2),
        @SerializedName("3") IMAGE(3),
        @SerializedName("4") VIDEO(4),
        @SerializedName("5") DELETE(5);

        @Getter
        @JsonValue
        private final int type;

        ScreenStateCode(int screenStateCode) { this.type = screenStateCode; }
    }

    enum ScreenMappingType {
        @SerializedName("1") EVENT(1),
        @SerializedName("2") PROGRAM(2),
        @SerializedName("3") SESSION(3),
        @SerializedName("4") LOUNGE(4);

        @Getter
        @JsonValue
        private final int type;

        ScreenMappingType(int screenMappingType) { this.type = screenMappingType; }
    }

    enum ScreenDisplayStateCode {
        @SerializedName("1") USE(1),
        @SerializedName("2") NONE_USE(2),
        @SerializedName("3") DELETE(3);

        @Getter
        @JsonValue
        private final int type;

        ScreenDisplayStateCode(int screenDisplayStateCode) { this.type = screenDisplayStateCode; }
    }

    enum PackageType {
        @SerializedName("1") SELECT(1),
        @SerializedName("2") ENTRANCE_ALL_IN_EVENT(2),
        @SerializedName("3") ENTRANCE_ALL_IN_PROGRAM(3),
        @SerializedName("4") ENTRANCE_TO_SESSION(4);

        @Getter
        @JsonValue
        private final int type;

        PackageType(int priceType) { this.type = priceType; }
    }

    enum PackageAuthorityCode {
        @SerializedName("5") DEFAULT(5),
        @SerializedName("2") SPEAKER(2),
        @SerializedName("3") STAFF(3),
        @SerializedName("1") VISITOR(1),
        @SerializedName("4") OPERATOR(4);

        @Getter
        @JsonValue
        private final int type;

        PackageAuthorityCode(int packageAuthorityCode) { this.type = packageAuthorityCode; }
    }

    enum PackageStateCode {
        @SerializedName("1") SALE(1),
        @SerializedName("2") STOP_SELLING(2),
        @SerializedName("3") ALL(3);

        @Getter
        @JsonValue
        private final int type;

        PackageStateCode(int packageStateCode) { this.type = packageStateCode; }
    }

    enum PackagePriceType {
        @SerializedName("1") FREE(1),
        @SerializedName("2") PAYMENT_EQUAL(1),
        @SerializedName("3") PAYMENT_DIFFERENTIAL(1);

        @Getter
        @JsonValue
        private final int type;

        PackagePriceType(int packagePriceType) { this.type = packagePriceType; }
    }

    enum PackageSearchDateType {
        @SerializedName("1") CREATE_DATE(1),
        @SerializedName("2") EVENT_DATE(2),
        @SerializedName("3") UPDATE_DATE(3);

        @Getter
        @JsonValue
        private final int type;

        PackageSearchDateType(int packageSearchDateType) { this.type = packageSearchDateType; }
    }

    enum PackageSearchNameAndCodeType {
        @SerializedName("1") EVENT_NAME(1),
        @SerializedName("2") EVENT_CODE(2),
        @SerializedName("3") PACKAGE_NAME(3),
        @SerializedName("4") PACKAGE_CODE(4);

        @Getter
        @JsonValue
        private final int type;

        PackageSearchNameAndCodeType(int packageNameAndCodeType) { this.type =packageNameAndCodeType; }
    }

    enum PackageSearchDomainAndCodeType {
        @SerializedName("1") HOST_NAME(1),
        @SerializedName("2") HOST_CODE(2);

        @Getter
        @JsonValue
        private final int type;

        PackageSearchDomainAndCodeType(int packageSearchHostCodeType) { this.type = packageSearchHostCodeType; }
    }

    enum PackageSearchStaffType {
        @SerializedName("1") ALL(1),
        @SerializedName("2") SPEAKER(2),
        @SerializedName("3") STAFF(3),
        @SerializedName("4") VISITOR(4),
        @SerializedName("5") OPERATOR(5);

        @Getter
        @JsonValue
        private final int type;

        PackageSearchStaffType(int packageSearchStaffType) { this.type = packageSearchStaffType; }
    }

    enum NoticeBoardArticleType {
        @SerializedName("1") SELECT(1),
        @SerializedName("2") INTEGRATED(2),
        @SerializedName("3") NORMAL(3);

        @Getter
        @JsonValue
        private final int type;

        NoticeBoardArticleType(int noticeBoardArticleType) { this.type = noticeBoardArticleType; }
    }

    enum NoticeBoardSearchDateType {
        @SerializedName("1") UPDATE_DATE(1),
        @SerializedName("2") CREATE_DATE(2);

        @Getter
        @JsonValue
        private final int type;

        NoticeBoardSearchDateType(int noticeBoardDateType) {
            this.type = noticeBoardDateType;
        }
    }

    enum NoticeBoardSearchArticleType {
        @SerializedName("1") ALL(1),
        @SerializedName("2") INTEGRATED(2),
        @SerializedName("3") NORMAL(3);


        @Getter
        @JsonValue
        private final int type;

        NoticeBoardSearchArticleType(int noticeBoardSearchArticleType) {
            this.type = noticeBoardSearchArticleType;
        }
    }

    enum NoticeBoardSearchRollingType {
        @SerializedName("1") ALL(1),
        @SerializedName("2") ROLLING(2),
        @SerializedName("3") NONE_ROLLING(3);


        @Getter
        @JsonValue
        private final int type;

        NoticeBoardSearchRollingType(int noticeBoardSearchRollingType) {
            this.type = noticeBoardSearchRollingType;
        }
    }

    enum NoticeBoardSearchTitleAndCreatorType {
        @SerializedName("1") TITLE(1),
        @SerializedName("2") UPDATE_USER_NAME(2),
        @SerializedName("3") UPDATE_USER_ID(3);


        @Getter
        @JsonValue
        private final int type;

        NoticeBoardSearchTitleAndCreatorType(int noticeBoardSearchTitleAndCreatorType) {
            this.type = noticeBoardSearchTitleAndCreatorType;
        }
    }

    @Getter
    enum LoungeTemplateCode {
        F_0001("f-0001"),
        F_0002("f-0002"),
        F_0003("f-0003"),
        F_0004("f-0004"),
        H_0001("h-0001"),
        H_0002("h-0002"),
        H_0003("h-0003"),
        H_0004("h-0004");
        private final String _loungeTemplateCode;

        LoungeTemplateCode(String loungeTemplateCode) {
            this._loungeTemplateCode = loungeTemplateCode;
        }

        public String getValue(){
            if(this._loungeTemplateCode == null)
                return "";
            else
                return this._loungeTemplateCode.toString();
        }
    }

    @Getter
    enum SpecialMeetupTemplateCode {
        S_0001("s-0001"),
        S_0002("s-0002"),
        S_0003("s-0003");
        private final String _specialMeetupTemplateCode;

        SpecialMeetupTemplateCode(String specialMeetupTemplateCode) {
            this._specialMeetupTemplateCode = specialMeetupTemplateCode;
        }

        public String getValue(){
            if(this._specialMeetupTemplateCode == null)
                return "";
            else
                return this._specialMeetupTemplateCode.toString();
        }
    }

    enum SpecialMeetupSessionType {
        @SerializedName("1") VIDEO(1),
        @SerializedName("2") VOICE(2);

        @Getter
        @JsonValue
        private final int type;

        SpecialMeetupSessionType(int specialMeetupSessionType) {
            this.type = specialMeetupSessionType;
        }
    }

    enum LoungeStateCode {
        @SerializedName("1") USE(1),
        @SerializedName("2") NONE_USE(2),
        @SerializedName("3") DELETE(3);

        @Getter
        @JsonValue
        private final int type;

        LoungeStateCode(int loungeStateCode) {
            this.type = loungeStateCode;
        }
    }
    enum LoungeSearchDateType {
        @SerializedName("1") CREATE_DATE(1),
        @SerializedName("2") LOUNGE_DATE(2),
        @SerializedName("3") UPDATE_DATE(3);

        @Getter
        @JsonValue
        private final int type;

        LoungeSearchDateType(int loungeSearchDateType) { this.type = loungeSearchDateType; }
    }
    enum LoungeSearchNameAndCodeType {
        @SerializedName("1") LOUNGE_NAME(1),
        @SerializedName("2") LOUNGE_CODE(2),
        @SerializedName("3") EVENT_NAME(3),
        @SerializedName("4") EVENT_CODE(4);
        @Getter
        @JsonValue
        private final int type;

        LoungeSearchNameAndCodeType(int loungeSearchNameAndCodeType) { this.type =loungeSearchNameAndCodeType; }
    }
    enum LoungeSearchDomainAndCodeType {
        @SerializedName("1") HOST_NAME(1),
        @SerializedName("2") HOST_CODE(2);

        @Getter
        @JsonValue
        private final int type;

        LoungeSearchDomainAndCodeType(int loungeSearchDomainAndCodeType) { this.type = loungeSearchDomainAndCodeType; }
    }

    enum AudienceSortType {
        @SerializedName("1") NAME(1),
        @SerializedName("2") NEW(2);

        @Getter
        @JsonValue
        private final int type;

        AudienceSortType(int visitorSortOrderType) { this.type = visitorSortOrderType; }
    }

    enum BannerSearchDateType {
        @SerializedName("1") UPDATE_DATE(1),
        @SerializedName("2") CREATE_DATE(2);

        @Getter
        @JsonValue
        private final int type;

        BannerSearchDateType(int bannerSearchDateType) { this.type = bannerSearchDateType; }
    }
    enum BannerCode {
        @SerializedName("1") ALL(1),
        @SerializedName("2") MOBILE(2),
        @SerializedName("3") PC(3);

        @Getter
        @JsonValue
        private final int type;

        BannerCode(int bannerCode) { this.type = bannerCode; }
    }
    enum BannerStateCode {
        @SerializedName("1") ALL(1),
        @SerializedName("2") USE(2),
        @SerializedName("3") NON_USE(3);
        @Getter
        @JsonValue
        private final int type;

        BannerStateCode(int bannerStateCode) { this.type = bannerStateCode; }
    }
    enum BannerSearchNameAndCodeType {
        @SerializedName("1") LOCATION_DESCRIPTION(1),
        @SerializedName("2") LOCATION(2),
        @SerializedName("3") UPDATE_USER(1),
        @SerializedName("4") UPDATE_USER_ID(2);
        @Getter
        @JsonValue
        private final int type;

        BannerSearchNameAndCodeType(int bannerSearchNameAndCodeType) { this.type = bannerSearchNameAndCodeType; }
    }
    enum BannerDisplaySearchDateType {
        @SerializedName("1") UPDATE_DATE(1),
        @SerializedName("2") CREATE_DATE(2);

        @Getter
        @JsonValue
        private final int type;

        BannerDisplaySearchDateType(int bannerDisplaySearchDateType) { this.type = bannerDisplaySearchDateType; }
    }
    enum BannerDisplayStateCode {
        //@SerializedName("1") ALL(1),
        @SerializedName("2") USE(2),
        @SerializedName("3") NON_USE(3),
        @SerializedName("4") WAITING(4),
        @SerializedName("5") END(5);
        @Getter
        @JsonValue
        private final int type;

        BannerDisplayStateCode(int bannerDisplayStateCode) { this.type = bannerDisplayStateCode; }

        public static BannerDisplayStateCode fromValue(int value) {
            for (BannerDisplayStateCode bannerDisplayStateCode : BannerDisplayStateCode.values()) {
                if (bannerDisplayStateCode.getType() == value) {
                    return bannerDisplayStateCode;
                }
            }
            throw new IllegalArgumentException("Invalid FunctionType value: " + value);
        }

    }
    enum BannerDisplayStateCodeForSearch {
        @SerializedName("1") ALL(1),
        @SerializedName("2") USE(2),
        @SerializedName("3") NON_USE(3),
        @SerializedName("4") WAITING(4),
        @SerializedName("5") END(5);
        @Getter
        @JsonValue
        private final int type;

        BannerDisplayStateCodeForSearch(int bannerDisplayStateCode) { this.type = bannerDisplayStateCode; }

        public static BannerDisplayStateCode fromValue(int value) {
            for (BannerDisplayStateCode bannerDisplayStateCode : BannerDisplayStateCode.values()) {
                if (bannerDisplayStateCode.getType() == value) {
                    return bannerDisplayStateCode;
                }
            }
            throw new IllegalArgumentException("Invalid FunctionType value: " + value);
        }

    }

    enum BannerDisplaySearchNameAndCodeType {
        @SerializedName("1") LOCATION_DESCRIPTION(1),
        @SerializedName("2") LOCATION(2),
        @SerializedName("3") UPDATE_USER(1),
        @SerializedName("4") UPDATE_USER_ID(2);
        @Getter
        @JsonValue
        private final int type;

        BannerDisplaySearchNameAndCodeType(int bannerDisplaySearchNameAndCodeType) { this.type = bannerDisplaySearchNameAndCodeType; }
    }

    enum CouponLogDateType {
        @SerializedName("1") CREATE_DATE(1),
        @SerializedName("2") EVENT_DATE(2);

        @Getter
        @JsonValue
        private final int type;

        CouponLogDateType(int couponLogDateType) { this.type = couponLogDateType; }
    }

    enum CouponLogType {
        @SerializedName("5") ALL(5),
        @SerializedName("2") SPEAKER(2),
        @SerializedName("3") STAFF(3),
        @SerializedName("1") VISITOR(1),
        @SerializedName("4") OPERATOR(4);

        @Getter
        @JsonValue
        private final int type;

        CouponLogType(int couponLogType) { this.type = couponLogType; }
    }

    enum CouponLogSearchNameAndCodeType {
        @SerializedName("1") EVENT_NAME(1),
        @SerializedName("2") EVENT_CODE(2),
        @SerializedName("3") PACKAGE_NAME(3),
        @SerializedName("4") PACKAGE_CODE(4);

        @Getter
        @JsonValue
        private final int type;

        CouponLogSearchNameAndCodeType(int couponLogSearchNameAndCodeType) { this.type =couponLogSearchNameAndCodeType; }
    }

    enum CouponLogSearchDomainAndCodeType {
        @SerializedName("1") HOST_NAME(1),
        @SerializedName("2") HOST_CODE(2);

        @Getter
        @JsonValue
        private final int type;

        CouponLogSearchDomainAndCodeType(int couponLogSearchDomainAndCodeType) { this.type = couponLogSearchDomainAndCodeType; }
    }

    enum LoungeType {
        @SerializedName("1") NORMAL(1),
        @SerializedName("2") EXPERIENCE(2);

        @Getter
        @JsonValue
        private final int type;

        LoungeType(int loungeType) { this.type = loungeType; }
    }

    enum OperatorAdminStaffType {
        @SerializedName("0") ALL(0),
        @SerializedName("5") SPEAKER(5),
        @SerializedName("6") STAFF(6);
        @Getter
        @JsonValue
        private final int type;

        OperatorAdminStaffType(int operatorAdminStaffType) { this.type = operatorAdminStaffType; }
    }

    enum OperatorAdminUseYn{
        @SerializedName("1") USE(1),
        @SerializedName("2") NOT_USE(2);
        @Getter
        @JsonValue
        private final int type;

        OperatorAdminUseYn(int operatorAdminUseYn) { this.type = operatorAdminUseYn; }
    }

    enum OperatorAdminSearchNameAndId {
        @SerializedName("1") NAME(1),
        @SerializedName("2") ACCOUNT_NAME(2);
        @Getter
        @JsonValue
        private final int type;

        OperatorAdminSearchNameAndId(int operatorAdminSearchNameAndId) { this.type = operatorAdminSearchNameAndId; }
    }

    enum PartnerAdminStaffType {
        @SerializedName("7") PATNER(7);
        @Getter
        @JsonValue
        private final int type;

        PartnerAdminStaffType(int partnerAdminStaffType) { this.type = partnerAdminStaffType; }
    }
}

