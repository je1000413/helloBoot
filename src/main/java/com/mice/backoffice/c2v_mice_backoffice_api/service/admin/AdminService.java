package com.mice.backoffice.c2v_mice_backoffice_api.service.admin;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.admin.*;
import org.springframework.jdbc.UncategorizedSQLException;

public interface AdminService {
    C2VResponse checkAuth(Long curAdminId, Integer curMenuSeq, String method) throws UncategorizedSQLException;

    C2VResponse findAll(AdminListRequest req) throws C2VException;

    C2VResponse findAllById(long adminId) throws C2VException;

    C2VResponse addAdmin(AdminRequest req) throws C2VException;

    C2VResponse modifyAdmin(long adminId, AdminRequest req) throws C2VException;

    C2VResponse modifyAdminPassword(long adminId, AdminPasswordRequest req) throws C2VException;

    C2VResponse deleteAdmin(long adminId) throws C2VException;

    C2VResponse findDomain(DomainSearchRequest req) throws C2VException;

    C2VResponse addDomain(DomainRequest req) throws C2VException;

    C2VResponse modifyDomain(int domainId, DomainRequest req) throws C2VException;

    C2VResponse deleteDomain(int domainId) throws C2VException;

    C2VResponse getAdminAccountNameCheck(String accountName) throws C2VException;
}