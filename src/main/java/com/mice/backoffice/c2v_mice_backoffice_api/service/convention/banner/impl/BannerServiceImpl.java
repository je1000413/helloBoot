package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.banner.impl;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants;
import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.SequenceUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.BannerDisplayEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.BannerEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.banner.*;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.BannerDisplayRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.BannerRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.mybatis.BannerMybatisRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.LanguageService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.banner.BannerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BannerServiceImpl implements BannerService {

    /* Repository */
    @Autowired
    BannerMybatisRepository bannerMybatisRepository;
    @Autowired
    BannerDisplayRepository bannerDisplayRepository;
    @Autowired
    BannerRepository bannerRepository;
    /* Service */
    @Autowired
    LanguageService languageService;
    @Override
    public C2VResponse list(BannerListRequest req) {

        BannerListSqlRequest bannerListSqlRequest = new BannerListSqlRequest();

        //일자별
        if(req.getDateType() != null && req.getStartDateTime() != null && req.getEndDateTime() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:00");
            String startDatetime = req.getStartDateTime().format(formatter);
            String endDatetime = req.getEndDateTime().format(formatter);

            if (req.getDateType().equals(Supports.BannerSearchDateType.CREATE_DATE)) {
                bannerListSqlRequest.setStartCreateDatetime(startDatetime);
                bannerListSqlRequest.setEndCreateDatetime(endDatetime);
            } else if (req.getDateType().equals(Supports.BannerSearchDateType.UPDATE_DATE)) {
                bannerListSqlRequest.setStartUpdateDatetime(startDatetime);
                bannerListSqlRequest.setEndUpdateDatetime(endDatetime);
            }
        }
        if(req.getBannerCode() != null && !req.getBannerCode().equals(Supports.BannerCode.ALL))
            bannerListSqlRequest.setBannerCode(String.valueOf(req.getBannerCode().getType()));
        if(req.getBannerStateCode() != null && !req.getBannerStateCode().equals(Supports.BannerStateCode.ALL))
            bannerListSqlRequest.setBannerStateCode(String.valueOf(req.getBannerStateCode().getType()));

        //이름/코드
        if(req.getNameAndCodeType() != null && req.getNameAndCodeKeyword() != null){
            if(req.getNameAndCodeType().equals(Supports.BannerSearchNameAndCodeType.LOCATION_DESCRIPTION)){
                bannerListSqlRequest.setLocationDescription(req.getNameAndCodeKeyword());
            }else if(req.getNameAndCodeType().equals(Supports.BannerSearchNameAndCodeType.LOCATION)) {
                bannerListSqlRequest.setLocation(req.getNameAndCodeKeyword());
            }
        }

        if(req.getPageNum() != 0) bannerListSqlRequest.setPageNum(req.getPageNum());
        if(req.getPageSize() != 0) bannerListSqlRequest.setPageSize(req.getPageSize());


        List<Map<String,Object>> list = bannerMybatisRepository.findBannerListByCondition(RequestUtils.getSqlRequest(bannerListSqlRequest));
        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("Banner List 조회 성공").data(list).build();
    }

    @Override
    public C2VResponse displayList(BannerDisplayListRequest req) {

        BannerDisplayListSqlRequest bannerDisplayListSqlRequest = new BannerDisplayListSqlRequest();

        //일자별
        if(req.getDateType() != null && req.getStartDateTime() != null && req.getEndDateTime() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:00");
            String startDatetime = req.getStartDateTime().format(formatter);
            String endDatetime = req.getEndDateTime().format(formatter);

            if (req.getDateType().equals(Supports.BannerDisplaySearchDateType.CREATE_DATE)) {
                bannerDisplayListSqlRequest.setStartCreateDatetime(startDatetime);
                bannerDisplayListSqlRequest.setEndCreateDatetime(endDatetime);
            } else if (req.getDateType().equals(Supports.BannerDisplaySearchDateType.UPDATE_DATE)) {
                bannerDisplayListSqlRequest.setStartUpdateDatetime(startDatetime);
                bannerDisplayListSqlRequest.setEndUpdateDatetime(endDatetime);
            }
        }
        if(req.getBannerCode() != null && !req.getBannerCode().equals(Supports.BannerCode.ALL))
            bannerDisplayListSqlRequest.setBannerCode(String.valueOf(req.getBannerCode().getType()));
        if(req.getBannerDisplayStateCode() != null && !req.getBannerDisplayStateCode().equals(Supports.BannerDisplayStateCodeForSearch.ALL))
            bannerDisplayListSqlRequest.setBannerDisplayStateCode(String.valueOf(req.getBannerDisplayStateCode().getType()));

        //이름/코드
        if(req.getNameAndCodeType() != null){
            if(req.getNameAndCodeType().equals(Supports.BannerDisplaySearchNameAndCodeType.LOCATION_DESCRIPTION)){
                bannerDisplayListSqlRequest.setLocationDescription(req.getNameAndCodeKeyword());
            }else if(req.getNameAndCodeType().equals(Supports.BannerDisplaySearchNameAndCodeType.LOCATION)) {
                bannerDisplayListSqlRequest.setLocation(req.getNameAndCodeKeyword());
            }
        }

        if(req.getPageNum() != 0) bannerDisplayListSqlRequest.setPageNum(req.getPageNum());
        if(req.getPageSize() != 0) bannerDisplayListSqlRequest.setPageSize(req.getPageSize());

        List<Map<String,Object>> list = bannerMybatisRepository.findBannerDisplayListByCondition(RequestUtils.getSqlRequest(bannerDisplayListSqlRequest));
        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("Banner Display List 조회 성공").data(list).build();
    }

    @Override
    public C2VResponse getDisplay(long displaySeq) {

        BannerDisplayEntity bde = bannerDisplayRepository.findByDisplaySeq(displaySeq);
        if(bde == null) throw new EntityNotFoundException((String.format("Does not exist bannerId: %s", bde.getBannerId())));
        BannerEntity be = bannerRepository.findByBannerId(bde.getBannerId());
        if(be == null) throw new EntityNotFoundException((String.format("Does not exist bannerId: %s", bde.getBannerId())));

        BannerDisplayGetResponse bannerDisplayGetResponse = new BannerDisplayGetResponse();

        bannerDisplayGetResponse.setBannerId(bde.getBannerId());
        bannerDisplayGetResponse.setLocation(be.getLocation());
        bannerDisplayGetResponse.setLocationDiscription(be.getLocationDescription());
        bannerDisplayGetResponse.setSizeX(be.getSizeX());
        bannerDisplayGetResponse.setSizeY(be.getSizeY());
        bannerDisplayGetResponse.setBannerCode(be.getBannerCode());
        bannerDisplayGetResponse.setStateCode(Supports.BannerDisplayStateCode.fromValue(bde.getStateCode()));
        bannerDisplayGetResponse.setAlwaysDisplayYn(bde.getAlwaysDisplayYn());
        bannerDisplayGetResponse.setDisplayStartDatetime(bde.getDisplayStartDatetime());
        bannerDisplayGetResponse.setDisplayEndDatetime(bde.getDisplayEndDatetime());
        bannerDisplayGetResponse.setDisplayContents(languageService.makeLanguageMap(bde.getDisplayContents()));
        bannerDisplayGetResponse.setLinkAddress(languageService.makeLanguageMap(bde.getLinkAddress()));

        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("Banner Display 조회 성공").data(bannerDisplayGetResponse).build();
    }

    @Override
    public C2VResponse createDisplay(BannerDisplayCreateRequest req) throws C2VException {
        String seq = SequenceUtils.getSequence();
        LocalDateTime now = LocalDateTime.now();

        BannerDisplayEntity bde = BannerDisplayEntity.builder()
                .bannerId(req.getBannerId())
                .pageNo(1)
                .displayContents(String.format("banner_display_contents_%s", seq))
                .linkAddress(String.format("banner_link_address_%s", seq))
                .alwaysDisplayYn(req.getAlwaysDisplayYn())
                .displayStartDatetime((req.getAlwaysDisplayYn() == 'Y') ? now.minusMinutes(1) : req.getDisplayStartDatetime())
                .displayEndDatetime((req.getAlwaysDisplayYn() == 'Y') ? now.minusMinutes(1) : req.getDisplayEndDatetime())
                .stateCode(req.getStateCode().getType())
                .createUserId(RequestUtils.getCurAdminId())
                .updateUserId(RequestUtils.getCurAdminId())
                .createDatetime(now)
                .updateDatetime(now).build();

        Map<String, Map<Supports.LanguageType,String>> languageMap = new HashMap<>();
        languageMap.put(String.format("banner_display_contents_%s", seq), req.getDisplayContents());
        languageMap.put(String.format("banner_link_address_%s", seq), req.getLinkAddress());
        List<LanguageEntity> result = languageService.makeLanguageEntities(languageMap);

        // 언어 저장
        languageService.saveAll(result);

        // 배너디스플레이 저장
        bannerDisplayRepository.save(bde);

        return C2VResponse.builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg("Banner Display 생성 성공")
                .data(null)
                .build();
    }

    @Override
    public C2VResponse updateDisplay(long displaySeq, BannerDisplayCreateRequest request) {

        BannerDisplayEntity bde = bannerDisplayRepository.findByDisplaySeq(displaySeq);
        if(bde == null) throw new EntityNotFoundException((String.format("Does not exist bannerId: %s", bde.getBannerId())));

        bde.updateBannerDisplay(request.getStateCode(), request.getAlwaysDisplayYn(), request.getDisplayStartDatetime(), request.getDisplayEndDatetime());

        if(request.getDisplayContents().size() == 0){
            languageService.deleteByLanguageCode(bde.getDisplayContents());
            languageService.deleteByLanguageCode(bde.getLinkAddress());
        }else{
            languageService.updateLanguageEntityList(request.getDisplayContents(), bde.getDisplayContents());
            languageService.updateLanguageEntityList(request.getLinkAddress(), bde.getLinkAddress());
        }

        bannerDisplayRepository.save(bde);

        return C2VResponse.builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg("Banner Display 수정 성공")
                .data(null)
                .build();
    }

    @Override
    public C2VResponse deleteDisplay(long displaySeq) {

        //존재 유무 확인
        BannerDisplayEntity bde = bannerDisplayRepository.findByDisplaySeq(displaySeq);
        if(bde == null) throw new EntityNotFoundException((String.format("Does not exist bannerId: %s", bde.getBannerId())));
        bde.deleteBannerDisplay(Supports.BannerDisplayStateCode.END);
        bannerDisplayRepository.save(bde);
        return C2VResponse.builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg("Banner Display 삭제 성공")
                .data(null)
                .build();
    }
}
