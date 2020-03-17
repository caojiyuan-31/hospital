package cn.ganwuwang.hospital.service;

import cn.ganwuwang.hospital.dao.UserRoleDao;
import cn.ganwuwang.hospital.domain.constant.ResultEnum;
import cn.ganwuwang.hospital.domain.pojo.UserRole;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.query.PageQuery;
import cn.ganwuwang.hospital.domain.query.Sort;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl{

    @Autowired
    private UserRoleDao userRoleDao;

    public List<UserRole> queryPageList(Page page, List<Sort> sort, UserRole userRole) throws GlobalException {

        List<UserRole> result = null;
        PageQuery query = new PageQuery(userRole);
        query.setPage(page);
        if(sort != null){
            query.setSort(sort);
        }

        try{
            result = userRoleDao.queryList(query);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

        return result;
    }

    public Integer queryTotal(UserRole userRole) throws GlobalException {

        Integer result = 0;
        PageQuery query = new PageQuery(userRole);

        try{
            result = userRoleDao.queryTotal(query);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

        return result;
    }

    public UserRole queryObject(Long id) throws GlobalException {

        UserRole result = null;
        try{
            result = userRoleDao.queryObject(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }
        return result;
    }

    public void save(UserRole userRole) throws GlobalException {

        userRole.setDeleteFlag(false);
        userRole.setCreatedUser(CheckUtils.getAuthentication().getName());
        try{
            userRoleDao.save(userRole);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void update(UserRole userRole) throws GlobalException {

        userRole.setModifiedUser(CheckUtils.getAuthentication().getName());
        try{
            userRoleDao.update(userRole);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void deleteBatch(Long[] ids) throws GlobalException {

        try{
            userRoleDao.deleteBatch(ids);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void delete(Long id) throws GlobalException {

        try{
            userRoleDao.delete(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }
}
