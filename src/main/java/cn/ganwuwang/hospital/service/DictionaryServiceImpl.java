package cn.ganwuwang.hospital.service;

import cn.ganwuwang.hospital.dao.DictionaryDao;
import cn.ganwuwang.hospital.dao.DoctorDao;
import cn.ganwuwang.hospital.domain.constant.ResultEnum;
import cn.ganwuwang.hospital.domain.pojo.Dictionary;
import cn.ganwuwang.hospital.domain.pojo.Doctor;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.query.PageQuery;
import cn.ganwuwang.hospital.domain.query.Sort;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictionaryServiceImpl {

    @Autowired
    private DictionaryDao dictionaryDao;

    public List<Dictionary> queryPageList(Page page, List<Sort> sort, Dictionary dictionary) throws GlobalException {

        List<Dictionary> result = null;
        PageQuery query = new PageQuery(dictionary);
        query.setPage(page);
        if(sort != null){
            query.setSort(sort);
        }

        try{
            result = dictionaryDao.queryList(query);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

        return result;
    }

    public Integer queryTotal(Dictionary dictionary) throws GlobalException {

        Integer result = 0;
        PageQuery query = new PageQuery(dictionary);

        try{
            result = dictionaryDao.queryTotal(query);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

        return result;
    }

    public Dictionary queryObject(Long id) throws GlobalException {

        Dictionary result = null;
        try{
            result = dictionaryDao.queryObject(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }
        return result;
    }

    public void save(Dictionary dictionary) throws GlobalException {

        dictionary.setDeleteFlag(false);
        dictionary.setCreatedUser(CheckUtils.getAuthentication().getName());
        try{
            dictionaryDao.save(dictionary);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void update(Dictionary dictionary) throws GlobalException {

        dictionary.setModifiedUser(CheckUtils.getAuthentication().getName());
        try{
            dictionaryDao.update(dictionary);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void deleteBatch(Long[] ids) throws GlobalException {

        try{
            dictionaryDao.deleteBatch(ids);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void delete(Long id) throws GlobalException {

        try{
            dictionaryDao.delete(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }
}
