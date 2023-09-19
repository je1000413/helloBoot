package com.mice.backoffice.c2v_mice_backoffice_api.config;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.converter.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.format.DateTimeFormatter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new LocalDateTimeConverter());

        // 행사 상품
        registry.addConverter(new PackageSearchDateTypeConverter());
        registry.addConverter(new PackageSearchDomainAndCodeTypeConverter());
        registry.addConverter(new PackageSearchNameAndCodeTypeConverter());
        registry.addConverter(new PackageSearchStaffTypeConverter());
        registry.addConverter(new PackageStateCodeConverter());

        //오퍼레이트
        registry.addConverter(new SessionStateCodeConverter());
        registry.addConverter(new EventStateCodeConverter());
        registry.addConverter(new EventSpaceStateCodeConverter());

        // 공지 사항
        registry.addConverter(new NoticeBoardSearchArticleTypeConverter());
        registry.addConverter(new NoticeBoardSearchDateTypeConverter());
        registry.addConverter(new NoticeBoardSearchRollingTypeConverter());
        registry.addConverter(new NoticeBoardSearchTitleAndCreatorTypeConverter());

        // 오퍼레이터 등록 질문
        registry.addConverter(new SessionQuestionListOrderConverter());
    }
}
