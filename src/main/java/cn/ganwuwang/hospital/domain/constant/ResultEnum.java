package cn.ganwuwang.hospital.domain.constant;

/**
 * 返回结果枚举类
 */
public enum ResultEnum {
    SUCCESS("00000","成功"),
    ERROR("00001", "未知异常"),
    DB_ERROR("00002", "数据库异常"),
    LOAD_ERROR("00003", "请登录"),
    DATA_ERROR("00004", "信息错误"),
    FILE_ERROR("00005", "文件类型错误"),
    UPLOAD_ERROR("00006", "文件上传错误"),
    EMAIL_ERROR("00007", "邮箱已被注册"),
    UNEMAIL_ERROR("00008", "邮箱未被注册"),
    IDENTITY_ERROR("00009", "身份证已被使用"),
    NAME_ERROR("00010", "用户名已存在"),
    REPLY_SELF("000011", "无法回复自己"),
    CHECK_ERROR("00012", "验证码错误"),
    CHECK_TIME_ERROR("00013", "验证码未过期"),
    REGISTER_FULL("00014", "该医生该时段挂号已满"),
    REGISTER_TWO("00015", "未看诊前请勿多次挂号"),
    REGISTER_CANNEL("00016", "该挂号已不可取消"),
    REGISTER_NOT("00017", "不存在挂号"),
    IDENTITY_UNKNOW("00018", "请完善身份信息和联系方式");

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
