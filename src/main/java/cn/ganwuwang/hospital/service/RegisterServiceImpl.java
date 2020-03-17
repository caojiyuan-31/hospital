package cn.ganwuwang.hospital.service;

import cn.ganwuwang.hospital.dao.RegisterDao;
import cn.ganwuwang.hospital.domain.constant.ResultEnum;
import cn.ganwuwang.hospital.domain.pojo.Register;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.query.PageQuery;
import cn.ganwuwang.hospital.domain.query.Sort;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterServiceImpl {

    @Autowired
    private RegisterDao registerDao;

    public List<Register> queryPageList(Page page, List<Sort> sort, Register register) throws GlobalException {

        List<Register> result = null;
        PageQuery query = new PageQuery(register);
        query.setPage(page);
        if(sort != null){
            query.setSort(sort);
        }

        try{
            result = registerDao.queryList(query);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

        return result;
    }

    public Integer queryTotal(Register register) throws GlobalException {

        Integer result = 0;
        PageQuery query = new PageQuery(register);

        try{
            result = registerDao.queryTotal(query);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

        return result;
    }

    public Register queryObject(Long id) throws GlobalException {

        Register result = null;
        try{
            result = registerDao.queryObject(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }
        return result;
    }

    public void save(Register register) throws GlobalException {

        register.setDeleteFlag(false);
        register.setCreatedUser(CheckUtils.getAuthentication().getName());
        try{
            registerDao.save(register);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void update(Register register) throws GlobalException {

        register.setModifiedUser(CheckUtils.getAuthentication().getName());
        try{
            registerDao.update(register);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void deleteBatch(Long[] ids) throws GlobalException {

        try{
            registerDao.deleteBatch(ids);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void delete(Long id) throws GlobalException {

        try{
            registerDao.delete(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }
}
