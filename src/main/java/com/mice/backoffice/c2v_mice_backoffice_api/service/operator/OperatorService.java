package com.mice.backoffice.c2v_mice_backoffice_api.service.operator;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.OperatorSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;

public interface OperatorService {
    C2VResponse getEventPrograms(long eventId) throws C2VException;
    C2VResponse getEventProgramSessions(long eventId, long programId) throws C2VException;
    C2VResponse getEventProgramDetail(long eventId, long programId, long sessionId) throws C2VException;
    C2VResponse getEventProgramSessionDisplays(long eventId, long programId, long sessionId) throws C2VException;
    C2VResponse setEventProgramSessionChange(long eventId, long programId, long stopSessionId, long playSessionId, SessionSupports.SessionStateCode stateCode) throws C2VException;
    C2VResponse setEventProgramSessionStateUpdate(long eventId, long programId, long sessionId, SessionSupports.SessionStateCode stateCode) throws C2VException;
    C2VResponse setEventProgramSessionStreamingChange(long eventId, long programId, long sessionId, String source, Integer sourceType, OperatorSupports.OnOffType isOnOffType) throws C2VException;
    C2VResponse setEventProgramSessionMotionChange(long eventId, long programId, long sessionId, String motionType, String motionCode, OperatorSupports.OnOffType isOnOffType) throws C2VException;
    C2VResponse setEventProgramSessionMotionFavorite(String motionType, String motionCode) throws C2VException;
    C2VResponse getEventProgramSessionMotionFavoriteList() throws C2VException;

    C2VResponse setEventProgramSessionEffectChange(long eventId, long programId, long sessionId, String effectType, String effectCode, OperatorSupports.OnOffType isOnOffType) throws C2VException;
    C2VResponse getEventProgramSessionEffectFavoriteList() throws C2VException;

    C2VResponse jobOperatorCommandRetry() throws C2VException;
    C2VResponse setEventProgramStateChange(long eventId, long programId) throws C2VException;
    C2VResponse setEventStateChange(long eventId, EventSupports.EventStateCode stateCode, EventSupports.EventSpaceStateCode spaceStateCode) throws C2VException;
}
