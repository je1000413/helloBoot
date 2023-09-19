package com.mice.backoffice.c2v_mice_backoffice_api.service.chat.impl;

import com.com2verse.platform.object.C2VResponse;
import com.google.gson.Gson;
import com.mice.backoffice.c2v_mice_backoffice_api.client.GroupFeignClient;
import com.mice.backoffice.c2v_mice_backoffice_api.common.SequenceUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.OperatorSupports.CommandType;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.AudienceDto;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.AudienceListParam;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.GroupUserDto;
import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.AudienceListQueryParam;
import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.AudienceListResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.AudienceListResponse.AudienceInfo;
import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.MicrophonePatchRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.service.RedisService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.chat.AudienceGroupService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.AccountService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.operator.RedisPubService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class AudienceGroupServiceImpl implements AudienceGroupService {
    // Service
    private final AccountService accountService;
    private final RedisPubService redisPubService;
    private final RedisService redisService;

    // Repository

    @Override
    public AudienceListResponse groupList(long sessionId, AudienceListQueryParam param) throws IllegalAccessException {
        Set<Object> audienceList = redisService.getRedisAudiences(sessionId);
        PageRequest pageRequest = PageRequest.of(0, 10000000);

        // key accountId
        Map<Long, String> audienceNickNameMap = new HashMap<>();

        List<Long> convertAudienceList = audienceList
                .stream()
                .map(x -> {
                    long accountId = Long.parseLong(x.toString());

                    String nickName = redisService.getRedisAudienceNickName(x.toString());
                    audienceNickNameMap.put(accountId, nickName);

                    return accountId;
                })
                .toList();

        AudienceListParam findParam = AudienceListParam
                .builder()
                .audienceIds(convertAudienceList)
                .build();

        Page<AudienceDto> audiences = accountService.findAllByAccountId(findParam, pageRequest);
        List<AudienceInfo> audienceInfos = new ArrayList<>(audiences
                .getContent()
                .stream()
                .map(x -> {
                    String photoUrl = StringUtils.hasText(x.getPhotoPath()) ? redisService.getRedisAudienceProfile(String.format("/Profile/%s", x.getPhotoPath())) : "";

                    return AudienceInfo
                            .builder()
                            .departmentName(x.getDepartmentName())
                            .userName(audienceNickNameMap.get(x.getAccountId()))
                            .photoUrl(photoUrl)
                            .build();
                })
                .toList());

        if (param.isAsc()) {
            audienceInfos.sort(Comparator.comparing(AudienceInfo::getUserName));
        } else {
            audienceInfos.sort(Comparator.comparing(AudienceInfo::getUserName).reversed());
        }

        return AudienceListResponse
                .builder()
                .totalCount(audiences.getTotalElements())
                .visitors(audienceInfos)
                .build();
    }

    @Override
    public void kickOut(long sessionId, long userId) {
        String command = CommandType.UserKickOut.getValue();
        String commandKey = getCommandKey();

        Map<String, Object> commandMap = new HashMap<>();

        commandMap.put("command", command);
        commandMap.put("commandKey", commandKey);
        commandMap.put("sessionId", sessionId);
        commandMap.put("accountId", userId);

        redisPubService.publish(commandMap);
    }

    @Override
    public void manageMicrophone(long sessionId, long userId, MicrophonePatchRequest req) throws Exception {
        String command = CommandType.UserMicOnOff.getValue();
        String commandKey = getCommandKey();

        Map<String, Object> commandMap = new HashMap<>();

        commandMap.put("command", command);
        commandMap.put("commandKey", commandKey);
        commandMap.put("sessionId", sessionId);

        if (Character.toLowerCase(req.getMicrophoneYn()) == 'y') {
            commandMap.put("enableAccountId", userId);
            commandMap.put("disableAccountId", 0);
        } else {
            commandMap.put("enableAccountId", 0);
            commandMap.put("disableAccountId", userId);
        }

        redisPubService.publish(commandMap);
    }

    private String getCommandKey() {
        return RedisPubService.COMMAND_KEY + SequenceUtils.getSequence();
    }
}
