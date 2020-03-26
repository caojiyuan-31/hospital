package cn.ganwuwang.hospital.domain.pojo;

import java.io.Serializable;

public class UserAndCheck implements Serializable {

    private static final long serialVersionUID = 1L;
    private User user;
    private Integer check;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCheck() {
        return check;
    }

    public void setCheck(Integer check) {
        this.check = check;
    }
}
