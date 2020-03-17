package cn.ganwuwang.hospital.dao;


import cn.ganwuwang.hospital.domain.query.PageQuery;

import java.util.List;

public interface BaseDao<T> {
	
	int save(T t);
	
	int update(T t);
	
	int delete(Object id);
	
	int deleteBatch(Object[] id);

	T queryObject(Object id);
	
	List<T> queryList(PageQuery<T> query);

	int queryTotal(PageQuery<T> query);

}
