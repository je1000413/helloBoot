package com.mice.backoffice.c2v_mice_backoffice_api.model.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MicrophonePatchRequest {
    private char microphoneYn;

    public MicrophonePatchRequest(char microphoneYn) {
        this.microphoneYn = microphoneYn;
    }
}
