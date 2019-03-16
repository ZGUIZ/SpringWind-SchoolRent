package com.baomidou.springwind.entity;

public class CheckStatementExtend  extends CheckStatement{
    private int page;
    private int pageSize;

    public CheckStatementExtend() {
        pageSize = 15;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getStart(){
        return (page-1) * pageSize;
    }

    public int getPageSize(){
        return pageSize;
    }

    public void setPageSize(int pageSize){
        this.pageSize = pageSize;
    }
}
