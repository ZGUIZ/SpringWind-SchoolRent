package com.baomidou.springwind.entity;

public class EvalExtend {
    private int page;
    private int pageSize;

    public EvalExtend() {
        pageSize = 10;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStart(){
        return (page-1) * pageSize;
    }
}
