package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports.EventSellStateCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EventPatchTicketStatusRequest {
    private EventSellStateCode sellStateCode;
}
