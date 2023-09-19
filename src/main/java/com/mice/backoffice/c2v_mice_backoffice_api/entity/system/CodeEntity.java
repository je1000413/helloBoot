package com.mice.backoffice.c2v_mice_backoffice_api.entity.system;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@NoArgsConstructor
@Table(name = "sys_code_info")
@Entity
public class CodeEntity {

    @EmbeddedId
    private CodeEntityPK codeEntityPK;

    @Column(name = "cd_typ", columnDefinition = "char", length = 8, insertable=false, updatable=false)
    private @Getter String cdTyp;

    @Column(name = "cd", nullable = false, insertable=false, updatable=false)
    private @Getter Integer cd;

    @Column(name = "cd_nm")
    private @Getter String cdNm;

    @Column(name = "cd_desc")
    private @Getter String cdDesc;

    @Column(name = "use_yn")
    private @Getter Integer useYn;

    public @Builder CodeEntity(String cdTyp, Integer cd, String cdNm, String cdDesc, Integer useYn) {
        this.cdTyp = cdTyp;
        this.cd = cd;
        this.cdNm = cdNm;
        this.cdDesc = cdDesc;
        this.useYn = useYn;
    }


    @NoArgsConstructor
    @Embeddable
    public static class CodeEntityPK implements Serializable{

        @Column(name = "cd_typ", columnDefinition = "char", length = 8, nullable = false, insertable=false, updatable=false)
        private String cdTyp;

        @Column(name = "cd", columnDefinition = "int", nullable = false, insertable=false, updatable=false)
        private Integer cd;
    }

}