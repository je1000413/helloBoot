package com.mice.backoffice.c2v_mice_backoffice_api.repository.mybatis;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.system.CodeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TestRepository {
    public List<CodeEntity> getCodeList(@Param("in_cd_typ") String cdType, @Param("in_cd") int cd);
    public List<List<Object>> getCodeListTest();
    public int adminRoleCheck(@Param("in_cur_admin_id") long curAdminId,
                              @Param("in_cur_menu_seq") int curMenuSeq,
                              @Param("in_method") String inMethod);
}
