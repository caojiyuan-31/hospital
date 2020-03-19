package cn.ganwuwang.hospital.dao;

import cn.ganwuwang.hospital.domain.pojo.Data;

import java.util.List;

public interface DataDao extends BaseDao<Data> {

    List<Data> queryListByCategory(String category);

    int queryTotalByCategory(String category);

}
