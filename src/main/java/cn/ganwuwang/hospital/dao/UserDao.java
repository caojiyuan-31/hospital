package cn.ganwuwang.hospital.dao;

import cn.ganwuwang.hospital.domain.pojo.User;
import cn.ganwuwang.hospital.domain.query.PageQuery;

import java.util.List;

public interface UserDao extends BaseDao<User> {

    User queryObjectByUserName(String userName);

    List<User> queryListByUserName(PageQuery<User> query);

    Integer queryTotalByUserName(PageQuery<User> query);

}
