package com.mice.backoffice.c2v_mice_backoffice_api.entity.operator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mice.backoffice.c2v_mice_backoffice_api.common.MapToJsonConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.ArrayList;

@Data
@Entity
public class OpMotionEntity {
    @Id
    @Column(name="row_num")
    private Integer rowNum;

    @JsonIgnore
    @Convert(converter = MapToJsonConverter.class)
    @Column(name="motion_tags")
    private ArrayList<String> motionTags;

    @Column(name="motion_code")
    private String motionCode;
}
