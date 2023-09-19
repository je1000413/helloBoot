package com.mice.backoffice.c2v_mice_backoffice_api.model.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.NoticeBoardArticleType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class NoticeBoardUpdateRequest {
    @NotNull(message = "articleType is null")
    private NoticeBoardArticleType articleType;
    @NotNull(message = "articleTitle is null")
    private Map<LanguageType, String> articleTitle;
    @NotNull(message = "articleDescription is null")
    private Map<LanguageType, String> articleDescription;
    private char mainYn;

    public Map<LanguageType, String> getArticleTitle() {
        return Objects.isNull(articleTitle) || articleTitle.isEmpty() ? null : Collections.unmodifiableMap(articleTitle);
    }

    public Map<LanguageType, String> getArticleDescription() {
        return Objects.isNull(articleDescription) || articleDescription.isEmpty() ? null : Collections.unmodifiableMap(articleDescription);
    }
}
