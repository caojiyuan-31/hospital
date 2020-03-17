package cn.ganwuwang.hospital.dao;

import cn.ganwuwang.hospital.domain.pojo.User;

public interface UserDao extends BaseDao<User> {

    User queryObjectByUserName(String userName);

}
