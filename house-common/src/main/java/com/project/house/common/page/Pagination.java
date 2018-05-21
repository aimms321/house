package com.project.house.common.page;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by user on 2018-05-21.
 */
public class Pagination {
    private Integer pageNum;
    private Integer pageSize;
    private long totalCount;
    private List<Integer> pageList= Lists.newArrayList();

    public Pagination(Integer pageNum, Integer pageSize, Long totalCount) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        for (int i = 1; i <= pageNum; i++) {
            pageList.add(i);
        }
        Long pageCount = totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1);

        for (int i = pageNum+1; i <pageCount ; i++) {
            pageList.add(i);
        }
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<Integer> getPageList() {
        return pageList;
    }

    public void setPageList(List<Integer> pageList) {
        this.pageList = pageList;
    }
}
