package com.mice.backoffice.c2v_mice_backoffice_api.model.chat;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ChatSupports.AudienceSortType;
import lombok.*;

@Getter
@AllArgsConstructor
public class AudienceListQueryParam {
    private AudienceSortType sortType;
    private boolean asc;
}