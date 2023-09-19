package com.mice.backoffice.c2v_mice_backoffice_api.controller.v1;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.OperatorSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.model.operator.OpSessionOperatorRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.service.operator.OperatorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/operator")
public class OperatorController {

    private final OperatorService operatorService;

    @Operation(summary = "행사>프로그램리스트", description = "행사정보 및 프로그램 리스트 정보 제공")
    @GetMapping("/{eventId}/programs")
    public C2VResponse getEventPrograms(@PathVariable(name = "eventId") long eventId) throws C2VException  {
        return operatorService.getEventPrograms(eventId);
    }

    @Operation(summary = "프로그램>세션타임라인", description = "프로그램별 세션의 타임라인과 상태정보 제공")
    @GetMapping("/{eventId}/{programId}/sessions")
    public C2VResponse getEventProgramSessions(@PathVariable(name = "eventId") long eventId,
                                               @PathVariable(name = "programId") long programId) throws C2VException {
        return operatorService.getEventProgramSessions(eventId, programId);
    }

    @Operation(summary = "프로그램 상세정보", description = "프로그램의 일정 세션의 정보, 강사정보등을 제공")
    @GetMapping("/{eventId}/{programId}/{sessionId}/detail")
    public C2VResponse getEventProgramDetail(@PathVariable(name = "eventId") long eventId,
                                             @PathVariable(name = "programId") long programId,
                                            @PathVariable(name = "sessionId") long sessionId) throws C2VException {
        return operatorService.getEventProgramDetail(eventId, programId, sessionId);
    }


    @Operation(summary = "프로그램 상세정보", description = "세션의 소스 리스트 제공 ")
    @GetMapping("/{eventId}/{programId}/{sessionId}/displays")
    public C2VResponse getEventProgramSessionDisplays(@PathVariable(name = "eventId") long eventId,
                                             @PathVariable(name = "programId") long programId,
                                             @PathVariable(name = "sessionId") long sessionId) throws C2VException {
        return operatorService.getEventProgramSessionDisplays(eventId, programId, sessionId);
    }

    @Operation(summary = "세션 변경", description = "이전세션 종료하고 신규세션을 시작한다.")
    @PutMapping("/{eventId}/{programId}/sessionChange/{stopSessionId}/{playSessionId}/{stateCode}")
    public C2VResponse setEventProgramSessionChange(@PathVariable(name = "eventId") long eventId,
                                                    @PathVariable(name = "programId") long programId,
                                                    @PathVariable(name = "stopSessionId") long stopSessionId,
                                                    @PathVariable(name = "playSessionId") long playSessionId,
                                                    @PathVariable(name = "stateCode") SessionSupports.SessionStateCode stateCode) throws C2VException {
        return operatorService.setEventProgramSessionChange(eventId, programId, stopSessionId, playSessionId, stateCode);
    }

    @Operation(summary = "세션 상태변경", description = "세션의 상태를 변경 합니다.")
    @PutMapping("/{eventId}/{programId}/{sessionId}/stateChange/{stateCode}")
    public C2VResponse setEventProgramSessionStateChange(@PathVariable(name = "eventId") long eventId,
                                                         @PathVariable(name = "programId") long programId,
                                                         @PathVariable(name = "sessionId") long sessionId,
                                                         @PathVariable(name = "stateCode") SessionSupports.SessionStateCode stateCode) throws C2VException {
        return operatorService.setEventProgramSessionStateUpdate(eventId, programId, sessionId, stateCode);

    }

    @Operation(summary = "세션 스트리밍 ON/OFF", description = "세션 스트리밍 ON / OFF")
    @PutMapping("/{eventId}/{programId}/{sessionId}/streamingChange/{isOnOffType}")
    public C2VResponse setEventProgramSessionStreamingChange(@PathVariable(name = "eventId") long eventId,
                                                             @PathVariable(name = "programId") long programId,
                                                             @PathVariable(name = "sessionId") long sessionId,
                                                             @PathVariable(name="isOnOffType") OperatorSupports.OnOffType isOnOffType,
                                                             @RequestBody OpSessionOperatorRequest opSessionOperatorRequest) throws C2VException {
        return operatorService.setEventProgramSessionStreamingChange(eventId, programId, sessionId, opSessionOperatorRequest.getSource(), opSessionOperatorRequest.getSourceType().getType(), isOnOffType);
    }


    @Operation(summary = "모션 ON/OFF", description = "모션 ON / OFF")
    @PutMapping("/{eventId}/{programId}/{sessionId}/motionChange/{motionType}/{motionCode}/{isOnOffType}")
    public C2VResponse setEventProgramSessionMotionChange(@PathVariable(name = "eventId") long eventId,
                                                          @PathVariable(name = "programId") long programId,
                                                          @PathVariable(name = "sessionId") long sessionId,
                                                          @PathVariable(name = "motionType") String motionType,
                                                          @PathVariable(name = "motionCode") String motionCode,
                                                          @PathVariable(name="isOnOffType") OperatorSupports.OnOffType isOnOffType) throws C2VException {
        return operatorService.setEventProgramSessionMotionChange(eventId, programId, sessionId, motionType, motionCode, isOnOffType);
    }

    @Operation(summary = "모션 즐겨찾기", description = "모션에 대한 즐겨찾기 등록 및 해제")
    @PutMapping("/motions/motionFavorite/{motionType}/{motionCode}")
    public C2VResponse setEventProgramSessionMotionFavorite(@PathVariable(name = "motionType") String motionType,
                                                            @PathVariable(name = "motionCode") String motionCode) throws C2VException {
        return operatorService.setEventProgramSessionMotionFavorite(motionType, motionCode);
    }

    @Operation(summary = "모션 코드조회", description = "모션에 대한 코드리스트 및 즐겨찾기 여부 확인")
    @GetMapping("/motions")
    public C2VResponse getEventProgramSessionMotionFavoriteList() throws C2VException {
        return operatorService.getEventProgramSessionMotionFavoriteList();
    }

    @Operation(summary = "효과 ON/OFF", description = "효과 ON / OFF")
    @PutMapping("/{eventId}/{programId}/{sessionId}/effectChange/{effectType}/{effectCode}/{isOnOffType}")
    public C2VResponse setEventProgramSessionEffectChange(@PathVariable(name = "eventId") long eventId,
                                                          @PathVariable(name = "programId") long programId,
                                                          @PathVariable(name = "sessionId") long sessionId,
                                                          @PathVariable(name = "effectType") String effectType,
                                                          @PathVariable(name = "effectCode") String effectCode,
                                                          @PathVariable(name="isOnOffType") OperatorSupports.OnOffType isOnOffType) throws C2VException {
        return operatorService.setEventProgramSessionEffectChange(eventId, programId, sessionId, effectType, effectCode, isOnOffType);
    }

    @Operation(summary = "효과 코드조회", description = "효과에 대한 코드리스트 및 즐겨찾기 여부 확인")
    @GetMapping("/effects")
    public C2VResponse getEventProgramSessionEffectFavoriteList() throws C2VException {
        return operatorService.getEventProgramSessionEffectFavoriteList();
    }


    @Operation(summary = "프로그램 전체 종료", description = "현재 프로그램의 세션을 전체 종료 처리 함.")
    @PutMapping("/{eventId}/{programId}/stateChange/finish")
    public C2VResponse setEventProgramStateChange(@PathVariable(name = "eventId") long eventId, @PathVariable(name = "programId") long programId) throws C2VException {
        return operatorService.setEventProgramStateChange(eventId, programId);
    }

    @Operation(summary = "행사 상태 변경", description = "행사에 대한 시작, 종료를 하며 연동에 필요한 키를 발급하고 관리 한다.")
    @PutMapping("/{eventId}/stateChange/{stateCode}")
    public C2VResponse setEventStateChange(@PathVariable(name = "eventId") long eventId, @PathVariable(name = "stateCode") EventSupports.EventStateCode stateCode) throws C2VException {
        return operatorService.setEventStateChange(eventId, stateCode, null);
    }

    @Operation(summary = "행사 공간상태 변경", description = "행사에 대한 공간상태코드를 관리한다.")
    @PutMapping("/{eventId}/spaceStateChange/{spaceStateCode}")
    public C2VResponse setEventSpaceStateChange(@PathVariable(name = "eventId") long eventId, @PathVariable(name = "spaceStateCode") EventSupports.EventSpaceStateCode spaceStateCode) throws C2VException {
        return operatorService.setEventStateChange(eventId, null,spaceStateCode);
    }
}
