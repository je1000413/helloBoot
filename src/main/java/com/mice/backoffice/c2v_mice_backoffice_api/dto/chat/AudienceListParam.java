package com.mice.backoffice.c2v_mice_backoffice_api.dto.chat;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ChatSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ChatSupports.AudienceSortType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AudienceListParam {
    private List<Long> audienceIds;

    @Builder
    public AudienceListParam(List<Long> audienceIds) {
        this.audienceIds = audienceIds;
    }
}
