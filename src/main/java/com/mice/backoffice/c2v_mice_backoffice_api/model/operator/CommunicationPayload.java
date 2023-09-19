package com.mice.backoffice.c2v_mice_backoffice_api.model.operator;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommunicationPayload {
    private GroupServerResponse.Area area;
    private GroupServerResponse.Stream stream;
    private GroupServerResponse.Conference conference;
}
