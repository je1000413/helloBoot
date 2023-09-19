package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base;

import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class JpaBaseUserEntity extends JpaBaseTimeEntity{
    @Column(updatable = false, name = "create_user_id")
    private Long createUserId;
    @Column(name = "update_user_id")
    private Long updateUserId;

    public JpaBaseUserEntity(long createUserId, long updateUserId) {
        this.createUserId = createUserId;
        this.updateUserId = updateUserId;
    }

    public void updateUserId (long updateUserId) {
        this.updateUserId = updateUserId;
    }
}
