package cn.ganwuwang.hospital.domain.results;

import cn.ganwuwang.hospital.domain.constant.ResultEnum;

public class Result {

    private String code;
    private String msg;
    private Object data;

    public Result(){
        this.code = ResultEnum.SUCCESS.getCode();
        this.msg = ResultEnum.SUCCESS.getMsg();
    }

    public Result(Object data){
        this.code = ResultEnum.SUCCESS.getCode();
        this.msg = ResultEnum.SUCCESS.getMsg();
        this.data = data;
    }

    public Result(ResultEnum r){
        this.code = r.getCode();
        this.msg = r.getMsg();
    }

    public Result(GlobalException e){
        this.code = e.getCode();
        this.msg = e.getMsg();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
