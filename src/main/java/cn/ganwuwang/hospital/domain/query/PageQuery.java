package cn.ganwuwang.hospital.domain.query;


import cn.ganwuwang.hospital.utils.GsonUtils;

import java.io.Serializable;
import java.util.List;

public class PageQuery<T> extends Query<T> implements Serializable {

    private Page page;
    private List<Sort> sort;
    private List<Long> idList;

    public PageQuery() {
        super(null);
    }
    public PageQuery(T t) {
        super(t);
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<Sort> getSort() {
        return sort;
    }

    public void setSort(List<Sort> sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "PageQuery{" +
                "page=" + String.valueOf(page) +
                ", queryStr=" + String.valueOf(getQuery()) +
                ", sort=" + GsonUtils.toJson(sort) +
                '}';
    }
}
