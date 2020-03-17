package cn.ganwuwang.hospital.domain.results;

import cn.ganwuwang.hospital.domain.constant.ResultEnum;

public class GlobalException extends Exception{

    private String code;
    private String msg;

    public GlobalException(Throwable cause, ResultEnum r){
        super(cause);
        this.code = r.getCode();
        this.msg = r.getMsg();
    }

    public GlobalException(ResultEnum r){
        super();
        this.code = r.getCode();
        this.msg = r.getMsg();
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
