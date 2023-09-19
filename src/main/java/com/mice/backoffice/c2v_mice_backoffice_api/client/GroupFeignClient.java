package com.mice.backoffice.c2v_mice_backoffice_api.client;

import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.JoinGroupDto;
import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.AudienceListQueryParam;
import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.GroupJoinRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.operator.GroupServerRequest;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;

import org.springframework.web.bind.annotation.*;


@FeignClient(name = "groupFeignClientAdaptor", url = "${groupServer.domain}")
public interface GroupFeignClient {

    String AUTH_TOKEN = "Authorization";

    @GetMapping(value = "/groups/{groupId}")
    public C2VResponse list(@RequestHeader(AUTH_TOKEN) String bearerToken, @PathVariable("groupId") String groupId, @SpringQueryMap AudienceListQueryParam param);

    @GetMapping(value = "/groups/{groupId}/users")
    public C2VResponse visitorList(@RequestHeader(AUTH_TOKEN) String bearerToken, @PathVariable("groupId") String groupId);

    @GetMapping(value = "/groups/areas/{areaName}")
    public C2VResponse getArea(@RequestHeader(AUTH_TOKEN) String bearerToken, @PathVariable("areaName") String areaName);

    @PostMapping(value = "/areas")
    @Headers("Accept: application/json")
    public C2VResponse createArea(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestBody GroupServerRequest.Area request);

    @DeleteMapping(value = "/areas/{groupId}")
    public C2VResponse deleteArea(@RequestHeader(AUTH_TOKEN) String bearerToken, @PathVariable("groupId") String groupId);

    @PostMapping(value = "/streams")
    public C2VResponse createStream(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestBody GroupServerRequest.Stream request);

    @DeleteMapping(value = "/streams/{streamKey}")
    public C2VResponse deleteStream(@RequestHeader(AUTH_TOKEN) String bearerToken, @PathVariable("streamKey") String streamKey);

    @PostMapping(value = "/media/meetings")
    public C2VResponse createConferenceMediaRoom(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestBody GroupServerRequest.Conference request);

    @DeleteMapping(value = "/media/meetings/{roomId}")
    public C2VResponse deleteConferenceMediaRoom(@RequestHeader(AUTH_TOKEN) String bearerToken, @PathVariable("roomId") String roomId, @RequestParam("serviceName") String serviceName);

    @PostMapping(value = "/areas/move")
    public C2VResponse joinGroupArea(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestBody GroupJoinRequest dto);

    @PostMapping(value = "/space/users")
    public C2VResponse enterSpace(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestBody GroupJoinRequest dto);

    @DeleteMapping(value = "/space/users")
    public C2VResponse exitSpace(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestBody GroupJoinRequest dto);
}
