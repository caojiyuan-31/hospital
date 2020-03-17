package cn.ganwuwang.hospital.domain.constant;

public enum StatusEnum {

    ON_REGISTER(0,"已挂号"),
    CANCEL_REGISTER(1, "已取消挂号"),
    ON_DIAGNOSE(2, "已看诊"),
    CANCEL_DIAGNOSE(3, "未看诊");

    private StatusEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private Integer status;
    private String msg;

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

}
