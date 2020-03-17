package cn.ganwuwang.hospital.domain.query;

public class Page {

    /**
     * 起始页数
     */
    private Integer pageNo=1;
    /**
     * 每页条数
     */
    private Integer pageSize=20;


    public Page(){}

    public Page(Integer pageNo, Integer pageSize){
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }


    public Integer getOffset() {

        if(pageNo == 0){
            pageNo = 1;
        }

        return (this.pageNo - 1) * this.pageSize;
    }


    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }
}
