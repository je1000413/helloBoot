package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.event;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.*;
import com.mice.backoffice.c2v_mice_backoffice_api.exception.RaisErrorException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

public interface EventCustomRepository {
    @ExceptionHandler(RaisErrorException.class)
    List<EventListForDashBoardEntity> findEventForDashBoard(Map<String, Object> params);

    @ExceptionHandler(RaisErrorException.class)
    List<EventListEntity> findEventForList(Map<String, Object> params);

    @ExceptionHandler(RaisErrorException.class)
    List<EventShiftListEntity> findEventForShift(Map<String, Object> params);

    @ExceptionHandler(RaisErrorException.class)
    List<EventListForTimelineEntity> findEventForTimeline(Map<String, Object> params);

    @ExceptionHandler(RaisErrorException.class)
    List<EventListForManageSpaceEntity> findEventForManageSpace(Map<String, Object> params);

}
