package com.mice.backoffice.c2v_mice_backoffice_api.service.system;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.service.BaseService;

import java.io.IOException;

public interface CodeService extends BaseService {

    C2VResponse findAllByCdTyp(String mngTyp) throws C2VException;

    C2VResponse findByCdTypAndCd(String cdTyp, Integer cd) throws C2VException;

    C2VResponse jsonTOdb() throws C2VException, IOException;

    C2VResponse getSpaceTemplate() throws C2VException;

    C2VResponse getSpaceTemplate(String cdTyp) throws C2VException;

}
