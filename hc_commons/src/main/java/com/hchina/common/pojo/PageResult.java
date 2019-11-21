package com.hchina.common.pojo;


import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

    private Long total;     //总条数
    private Long totalPage; //总页数
    private List<T> item;   //当前页数据

    public PageResult() {
    }

    public PageResult(Long total, List<T> item) {
        this.total = total;
        this.item = item;
    }

    public PageResult(Long total, Long totalPage, List<T> item) {
        this.total = total;
        this.totalPage = totalPage;
        this.item = item;
    }
}
