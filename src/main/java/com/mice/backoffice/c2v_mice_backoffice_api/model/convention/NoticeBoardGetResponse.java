package com.mice.backoffice.c2v_mice_backoffice_api.model.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.NoticeBoardArticleType;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.NoticeBoardEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.admin.AdminInfoResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class NoticeBoardGetResponse {
    private int boardSeq;
    private NoticeBoardArticleType articleType;
    private Map<LanguageType, String> articleTitle;
    private Map<LanguageType, String> articleDescription;
    private char mainYn;
    private char useYn;

    private AdminInfoResponse createUserInfo;
    private AdminInfoResponse updateUserInfo;

    @Builder
    public NoticeBoardGetResponse(int boardSeq, NoticeBoardArticleType articleType, Map<LanguageType, String> articleTitle, Map<LanguageType, String> articleDescription,
                                  char mainYn, char useYn, AdminInfoResponse createUserInfo, AdminInfoResponse updateUserInfo) {
        this.boardSeq = boardSeq;
        this.articleType = articleType;
        this.articleTitle = articleTitle;
        this.articleDescription = articleDescription;
        this.mainYn = mainYn;
        this.useYn = useYn;
        this.createUserInfo = createUserInfo;
        this.updateUserInfo = updateUserInfo;
    }

    public static NoticeBoardGetResponseBuilder dataBuilder(NoticeBoardEntity nbe) {
        return builder()
                .boardSeq(nbe.getBoardSeq())
                .articleType(nbe.getArticleType())
                .mainYn(nbe.getMainYn())
                .useYn(nbe.getUseYn())
                .createUserInfo(nbe.getCreateUser().getUpdateUserInfo(nbe.getCreateDatetime()))
                .updateUserInfo(nbe.getUpdateUser().getCreateUserInfo(nbe.getUpdateDatetime()));
    }
}
