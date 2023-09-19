package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.lounge;


import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenCreateRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class LoungeCreateRequest {
    @NotNull(message = "eventId is null")
    private long eventId;
    @NotNull(message = "loungeName is null")
    private Map<Supports.LanguageType,String> loungeName;
    @NotNull(message = "templateId is null")
    private long templateId;
    @NotNull(message = "startDatetime is null")
    private LocalDateTime startDatetime;
    @NotNull(message = "endDatetime is null")
    private LocalDateTime endDatetime;
    private List<ScreenCreateRequest> screenList;
    @NotNull(message = "normalLoungeCnt is null")
    @Max(value = 20, message = "normalLoungeCnt has to be less than or equal to 20")
    private Integer normalLoungeCnt;
    @NotNull(message = "expLoungeCnt is null")
    @Max(value = 20, message = "expLoungeCnt has to be less than or equal to 20")
    private Integer expLoungeCnt;

    @Builder
    public LoungeCreateRequest(LoungeUpdateRequest lur) {

        this.eventId = lur.getEventId();
        this.loungeName = lur.getLoungeName();
        this.templateId = lur.getTemplateId();
        this.startDatetime = lur.getStartDatetime();
        this.endDatetime = lur.getEndDatetime();
        this.screenList = lur.getScreenList().stream().map(ScreenCreateRequest::makeCreateRequest).toList();
        this.normalLoungeCnt = lur.getNormalLoungeCnt();
        this.expLoungeCnt = lur.getExpLoungeCnt();
    }

}