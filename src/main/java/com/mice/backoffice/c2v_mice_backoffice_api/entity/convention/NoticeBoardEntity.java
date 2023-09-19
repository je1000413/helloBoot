package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.NoticeBoardArticleType;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseUserEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.NoticeBoardCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.NoticeBoardUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "notice_board")
@Getter
@SuperBuilder
public class NoticeBoardEntity extends JpaBaseUserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Column(name = "board_seq") Integer boardSeq;
    private @Column(name = "article_type") NoticeBoardArticleType articleType;
    private @Column(name = "main_yn") char mainYn;
    private @Column(name = "article_title") String articleTitle;
    private @Column(name = "article_description") String articleDescription;
    private @Column(name = "use_yn") char useYn;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_user_id", referencedColumnName = "admin_id", insertable = false, updatable = false)
    private AdminEntity createUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_user_id", referencedColumnName = "admin_id", insertable = false, updatable = false)
    private AdminEntity updateUser;

    public void update(NoticeBoardUpdateRequest req) {
        this.articleType = req.getArticleType();
        this.mainYn = req.getMainYn();
        updateUserId(RequestUtils.getCurAdminId());
    }

    public void delete() {
        this.useYn = 'n';
        updateUserId(RequestUtils.getCurAdminId());
    }

    public static NoticeBoardEntityBuilder dataBuilder(NoticeBoardCreateRequest req) {
        long currentUserId = RequestUtils.getCurAdminId();

        return builder()
                .articleType(req.getArticleType())
                .mainYn(req.getMainYn())
                .useYn('y')
                .createUserId(currentUserId)
                .createDatetime(LocalDateTime.now())
                .updateUserId(currentUserId)
                .updateDatetime(LocalDateTime.now());

    }
}
