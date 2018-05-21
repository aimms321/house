package com.project.house.common.page;


import java.util.List;

/**
 * Created by user on 2018-05-21.
 */
public class PageData<T> {
    private List<T> list;
    private Pagination pagination;

    public PageData(List<T> list, Pagination pagination) {
        this.list = list;
        this.pagination = pagination;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public static <T> PageData<T> build(List<T> list, Integer pageNum, Integer pageSize, Long totalCount) {
        Pagination pagination = new Pagination(pageNum, pageSize, totalCount);
        return new PageData<>(list, pagination);
    }
}
