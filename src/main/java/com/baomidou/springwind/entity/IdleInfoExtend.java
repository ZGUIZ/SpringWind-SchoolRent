package com.baomidou.springwind.entity;

public class IdleInfoExtend extends IdleInfo{
    private int page;
    private int pageSize;
    private String search;

    public IdleInfoExtend() {
        pageSize = 5;
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

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
