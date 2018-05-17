package com.project.house.common.page;

public class PageParams {

    private static final Integer PAGE_SIZE = 2;
    private Integer pageNum;
    private Integer pageSize;
    private Integer offset;
    private Integer limit;

    public PageParams(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.offset = pageSize * (pageNum - 1);
        this.limit = pageSize;
    }

    public PageParams() {
        this(PAGE_SIZE, 1);
    }

    public static PageParams bulid(Integer pageNum, Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        return new PageParams(pageNum, pageSize);
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

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
