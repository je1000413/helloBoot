package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.NoticeBoardCreateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Entity
@Table(name = "file_storage_key")
@Getter
public class FileStorageKeyEntity {

    @Id
    @Column(name = "customer_key")
    private String customerKey;

    @Column(name = "customer_auth_key")
    private String customerAuthKey;

    @Column(name = "create_datetime")
    private LocalDateTime createDatetime;

    @Column(name = "update_datetime")
    private LocalDateTime updateDatetime;

    public static FileStorageKeyEntityBuilder dataBuilder(String customerKey, String customerAuthKey) {
        return builder()
                .customerKey(customerKey)
                .customerAuthKey(customerAuthKey)
                .createDatetime(LocalDateTime.now())
                .updateDatetime(LocalDateTime.now());
    }

}
