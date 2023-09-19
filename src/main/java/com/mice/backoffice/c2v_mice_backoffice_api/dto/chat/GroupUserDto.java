package com.mice.backoffice.c2v_mice_backoffice_api.dto.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GroupUserDto {
    private int groupCount;
    private List<GroupUserDetailDto> users;

    @Builder
    public GroupUserDto(int groupCount, List<GroupUserDetailDto> users) {
        this.groupCount = groupCount;
        this.users = users;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class GroupUserDetailDto {
        private long userId;
        private int userRole;
        private String groupId;
        private int hivePlayerId;

        @Builder
        public GroupUserDetailDto(long userId, int userRole, String groupId,  int hivePlayerId) {
            this.userId = userId;
            this.userRole = userRole;
            this.groupId = groupId;
            this.hivePlayerId = hivePlayerId;
        }
    }
}
