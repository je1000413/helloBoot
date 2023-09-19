package com.mice.backoffice.c2v_mice_backoffice_api.common;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RowNumUtil {

    private long totalCount = 0;
    private long pageIndex = 0;
    private int recordCountPerPage = 0;
    private int itemSize = 0;

    private long rowNum = 0;


    public void pageCalc() {
        rowNum = this.totalCount < (this.totalCount - (this.pageIndex * this.recordCountPerPage)) ? this.itemSize : (this.totalCount - ((this.pageIndex - 1) * this.recordCountPerPage));
    }

    public long getRowNum() {
        long result = rowNum;
        rowNum--;
        return result;
    }

    public RowNumUtil(long totalCount,
                      long pageIndex,
                      int recordCountPerPage) {
        this.totalCount = totalCount;
        this.pageIndex = pageIndex;
        this.recordCountPerPage = recordCountPerPage;
        pageCalc();
    }

}