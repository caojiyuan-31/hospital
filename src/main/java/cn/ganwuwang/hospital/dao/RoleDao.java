package cn.ganwuwang.hospital.dao;

import cn.ganwuwang.hospital.domain.pojo.Role;

import java.util.List;

public interface RoleDao extends BaseDao<Role> {

    List<Role> getListByUid(Long uid);

}
