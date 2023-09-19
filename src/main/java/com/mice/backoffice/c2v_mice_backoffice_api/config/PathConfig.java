package com.mice.backoffice.c2v_mice_backoffice_api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PathConfig {
    @Value("${csvTableDataPath.emotion}")
    public String emotion;

    @Value("${csvTableDataPath.effect}")
    public String effect;

    @Value("${csvTableDataPath.spaceOption}")
    public String spaceOption;

    @Value("${csvTableDataPath.spaceTemplate}")
    public String spaceTemplate;

    @Value("${csvTableDataPath.SpaceTemplateArrangement}")
    public String spaceTemplateArrangement;

    @Value("${csvTableDataPath.spaceBaseObject}")
    public String spaceBaseObject;

    @Value("${csvTableDataPath.localizationStringForMice}")
    public String localizationStringForMice;

    @Value("${csvTableDataPath.localizationStringForOffice}")
    public String localizationStringForOffice;
}
