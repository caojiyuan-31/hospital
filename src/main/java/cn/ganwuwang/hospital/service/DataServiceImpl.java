package cn.ganwuwang.hospital.service;

import cn.ganwuwang.hospital.dao.DataDao;
import cn.ganwuwang.hospital.domain.constant.ResultEnum;
import cn.ganwuwang.hospital.domain.pojo.Data;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.query.PageQuery;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataServiceImpl {

    @Autowired
    private DataDao dataDao;

    public List<Data> queryPageList(Page page, Data data) throws GlobalException {

        List<Data> result = null;
        PageQuery query = new PageQuery(data);
        query.setPage(page);

        try{
            result = dataDao.queryList(query);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

        return result;
    }

    public Integer queryTotal(Data data) throws GlobalException {

        Integer result = 0;
        PageQuery query = new PageQuery(data);

        try{
            result = dataDao.queryTotal(query);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

        return result;
    }

    public Data queryObject(Long id) throws GlobalException {

        Data result = null;
        try{
            result = dataDao.queryObject(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }
        return result;
    }

    public void save(Data data) throws GlobalException {

        data.setDeleteFlag(false);
        data.setCreatedUser("SYSTEM");
        try{
            dataDao.save(data);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void update(Data data) throws GlobalException {

        data.setModifiedUser("SYSTEM");
        try{
            dataDao.update(data);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void deleteBatch(Long[] ids) throws GlobalException {

        try{
            dataDao.deleteBatch(ids);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void delete(Long id) throws GlobalException {

        try{
            dataDao.delete(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }
}
