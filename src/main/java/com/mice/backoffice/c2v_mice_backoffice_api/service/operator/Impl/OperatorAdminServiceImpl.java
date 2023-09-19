package com.mice.backoffice.c2v_mice_backoffice_api.service.operator.Impl;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.com2verse.platform.object.ResponseCode;
import com.mice.backoffice.c2v_mice_backoffice_api.client.AuthFeignClient;
import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants;
import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.redis.Auth;
import com.mice.backoffice.c2v_mice_backoffice_api.model.admin.AdminPasswordRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.admin.AdminRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.login.LoginRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.operator.*;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.admin.AdminRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.event.EventRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.mybatis.AdminMybatisRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.system.AuthRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.admin.AdminService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.login.LoginService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.operator.OperatorAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OperatorAdminServiceImpl implements OperatorAdminService {

    private final AdminRepository adminRepository;
    private final EventRepository eventRepository;
    private final AdminMybatisRepository adminMybatisRepository;
    private final LoginService loginService;
    private final AdminService adminService;
    private final AuthFeignClient authFeignClient;
    private final AuthRepository authRepository;
    @Value("${auth.serviceId}")
    private Integer serviceId;
    @Value("${tokens.system}")
    private String systemToken;


    @Override
    public C2VResponse partnerLogin(LoginRequest loginRequest) throws C2VException {

        AdminEntity adminEntity = loginService.findByAccountNameAndAccountPassword(loginRequest.getAccountName(), loginRequest.getAccountPassword());

        if(adminEntity.getGroupId() != Supports.PartnerAdminStaffType.PATNER.getType()) throw new C2VException(ResponseCode.BAD_REQUEST, "파트너 계정이 아닙니다.");

        int num = generateRandomSixDigitNumber();
        Auth auth = new Auth(loginRequest.getAccountName(), num);
        authRepository.save(auth);

        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("로그인 성공").data(auth).build();
    }

    @Override
    public C2VResponse partnerAuth(LoginRequest loginRequest, int authNumber) throws C2VException {

        AdminEntity adminEntity = loginService.findByAccountNameAndAccountPassword(loginRequest.getAccountName(), loginRequest.getAccountPassword());

        int num = authRepository.findById(loginRequest.getAccountName()).get().authNumber;
        if(authNumber!=num) throw new C2VException(ResponseCode.BAD_REQUEST, "인증번호가 일치하지 않습니다.");

        Map<String, Object> data = operatorLoginProcess(adminEntity.getAdminId(), adminEntity);

        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("로그인 성공").data(data).build();
    }

    @Override
    public C2VResponse login(LoginRequest loginRequest) throws C2VException {
        AdminEntity adminEntity = loginService.findByAccountNameAndAccountPassword(loginRequest.getAccountName(), loginRequest.getAccountPassword());

        if(adminEntity.getGroupId() != Supports.OperatorAdminStaffType.SPEAKER.getType() &&
                adminEntity.getGroupId() != Supports.OperatorAdminStaffType.STAFF.getType()) throw new C2VException(ResponseCode.BAD_REQUEST, "오퍼레이터 계정이 아닙니다.");

        Map<String, Object> data = operatorLoginProcess(adminEntity.getAdminId(), adminEntity);

        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("로그인 성공").data(data).build();
    }

    public Map<String, Object> operatorLoginProcess(long adminId, AdminEntity adminEntity){
        Map<String, Object> data = loginService.loginProcess(adminId, adminEntity);

        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("identifier", String.valueOf(adminId));
        requestMap.put("serviceId", serviceId);
        requestMap.put("serviceRole", "Operator");
        Map<String, String> authMap = (Map<String, String>) authFeignClient.getPartnerToken(new StringBuilder().append("Bearer ").append(systemToken).toString(),requestMap).getData();

        List<EventSupports.EventStateCode> eventStateCodeList =  new ArrayList<>(Arrays.asList(EventSupports.EventStateCode.OPENED, EventSupports.EventStateCode.IN_PROGRESS));
        List<String> eventIdList = eventRepository.findByStateCodeAndSpaceStateCode(eventStateCodeList, EventSupports.EventSpaceStateCode.OPEN);

        data.put("partnerToken", authMap.get("accessToken"));
        data.put("eventList", eventIdList);

        adminEntity.updateForLastLoginDatetime();
        adminRepository.save(adminEntity);

        return data;
    }

    @Override
    public C2VResponse list(OpAdminListRequest req) {

        OpAdminListSqlRequest opAdminListSqlRequest = new OpAdminListSqlRequest();

        if(req.getNameAndId() != null) {
            if (Supports.OperatorAdminSearchNameAndId.NAME.equals(req.getOperatorAdminSearchNameAndId())) {
                opAdminListSqlRequest.setOperatorName(req.getNameAndId());
            } else {
                opAdminListSqlRequest.setOperatorId(req.getNameAndId());
            }
        }
        opAdminListSqlRequest.setStaffType(req.getStaffType().getType());

        //페이지
        if(req.getPageNum() != 0) opAdminListSqlRequest.setPageNum(req.getPageNum());
        if(req.getPageSize() != 0) opAdminListSqlRequest.setPageSize(req.getPageSize());

        List<Map<String,Object>> list = adminMybatisRepository.findOperatorAdminListByCondition(RequestUtils.getSqlRequest(opAdminListSqlRequest));
        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("operator admin List 조회 성공").data(list).build();
    }

    @Override
    public C2VResponse get(long adminId) {
        AdminEntity ae = adminRepository.findByAdminId(adminId);
        Supports.OperatorAdminStaffType staffType = Supports.OperatorAdminStaffType.SPEAKER;
        Supports.OperatorAdminUseYn useYn = Supports.OperatorAdminUseYn.USE;;
        if(!ae.getGroupId().equals(5))staffType = Supports.OperatorAdminStaffType.STAFF;
        if(!ae.getStateCode().equals(1)) useYn = Supports.OperatorAdminUseYn.NOT_USE;

        OpAdminGetResponse opAdminGetResponse = OpAdminGetResponse.builder().ae(ae).staffType(staffType).useYn(useYn).build();
        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("operator admin 조회 성공").data(opAdminGetResponse).build();
    }

    @Override
    public C2VResponse create(OpAdminCreateRequest req) throws C2VException {

        AdminRequest ar = new AdminRequest();
        ar.setAccountName(req.getAccountName());
        ar.setAccountPassword(req.getAccountName());
        ar.setName(req.getName());
        ar.setDomainId(null);
        ar.setMailAddress(req.getMailAddress());
        ar.setTelNo(req.getTelNo());
        ar.setGroupId(req.getStaffType().getType());
        ar.setStateCode(Supports.OperatorAdminUseYn.USE.getType());
        ar.setNickname(req.getNickname());

        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("operator admin 생성 성공").data(adminService.addAdmin(ar).getData()).build();
    }

    @Override
    public C2VResponse update(long adminId, OpAdminUpdateRequest req) throws C2VException {

        AdminEntity ae = adminRepository.findByAdminId(adminId);

        AdminRequest ar = new AdminRequest();

        ar.setAccountName(ae.getAccountName());
        ar.setAccountPassword(ae.getAccountPassword());
        ar.setName(req.getName());
        ar.setDomainId(ae.getDomainId());
        ar.setMailAddress(req.getMailAddress());
        ar.setTelNo(req.getTelNo());
        ar.setGroupId(req.getStaffType().getType());
        ar.setStateCode(req.getStaffType().getType());
        ar.setNickname(req.getNickname());
        ar.setStateCode(req.getUseYn().getType());

        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("operator admin 생성 성공").data(adminService.modifyAdmin(adminId, ar).getData()).build();    }

    @Override
    public C2VResponse modifyAdminPassword(long adminId) throws C2VException {
        AdminEntity ae = adminRepository.findByAdminId(adminId);
        AdminPasswordRequest req = new AdminPasswordRequest();
        req.setAccountPassword(ae.getAccountName());
        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("operator admin 비밀번호 초기화 성공").data( adminService.modifyAdminPassword(adminId, req).getData()).build();    }

    @Override
    public C2VResponse modifyNewPassword(long adminId, AdminPasswordRequest req) throws C2VException {
        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("operator admin 비밀번호 변경 성공").data( adminService.modifyAdminPassword(adminId, req).getData()).build();    }

    @Override
    public C2VResponse getAdminAccountNameCheck(String accountName) throws C2VException {
        AdminEntity ae = adminRepository.findByAccountName(accountName);
        if(ae != null) throw new C2VException(ResponseCode.BAD_REQUEST, "이미 사용중인 계정입니다.");

        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("operator admin 계정 조회 성공").data(adminService.getAdminAccountNameCheck(accountName).getData()).build();    }

    public int generateRandomSixDigitNumber() {
        Random random = new Random();
        // Generate a random number between 100000 (inclusive) and 999999 (exclusive)
        int randomNumber = random.nextInt(900000) + 100000;
        return randomNumber;
    }

}
