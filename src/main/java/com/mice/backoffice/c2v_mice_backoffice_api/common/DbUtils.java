package com.mice.backoffice.c2v_mice_backoffice_api.common;

import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import java.util.Map;

public class DbUtils {

    public static StoredProcedureQuery convertStoreProcParameters(StoredProcedureQuery storedProcedureQuery, Map<String, Object> params){
        params.forEach((k, v) -> {
            if (v != null) {
                storedProcedureQuery.registerStoredProcedureParameter(k, v.getClass(), ParameterMode.IN).setParameter(k, v);
            } else {
                int nullType = java.sql.Types.NULL;
                storedProcedureQuery.registerStoredProcedureParameter(k, Object.class, ParameterMode.IN).setParameter(k, null);
            }
        });
        return storedProcedureQuery;
    }
}
