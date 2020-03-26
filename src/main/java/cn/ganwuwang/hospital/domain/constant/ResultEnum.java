package cn.ganwuwang.hospital.domain.constant;

/**
 * 返回结果枚举类
 */
public enum ResultEnum {
    SUCCESS("00000","成功"),
    ERROR("00001", "未知异常"),
    DB_ERROR("00002", "数据库异常"),
    LOAD_ERROR("00003", "登录错误"),
    DATA_ERROR("00004", "信息错误"),
    FILE_ERROR("00005", "文件类型错误"),
    UPLOAD_ERROR("00006", "文件上传错误"),
    EMAIL_ERROR("00007", "邮箱已被注册"),
    IDENTITY_ERROR("00008", "身份证已被使用"),
    NAME_ERROR("00009", "用户名已存在"),
    CHECK_ERROR("00010", "验证码错误");

    private ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
