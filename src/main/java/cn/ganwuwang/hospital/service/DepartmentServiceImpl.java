package cn.ganwuwang.hospital.service;

import cn.ganwuwang.hospital.dao.DepartmentDao;
import cn.ganwuwang.hospital.domain.constant.ResultEnum;
import cn.ganwuwang.hospital.domain.pojo.Department;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.query.PageQuery;
import cn.ganwuwang.hospital.domain.query.Sort;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl {

    @Autowired
    private DepartmentDao departmentDao;

    public List<Department> queryPageList(Page page, List<Sort> sort, Department department) throws GlobalException {

        List<Department> result = null;
        PageQuery query = new PageQuery(department);
        query.setPage(page);
        if(sort != null){
            query.setSort(sort);
        }

        try{
            result = departmentDao.queryList(query);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

        return result;
    }

    public Integer queryTotal(Department department) throws GlobalException {

        Integer result = 0;
        PageQuery query = new PageQuery(department);

        try{
            result = departmentDao.queryTotal(query);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

        return result;
    }

    public Department queryObject(Long id) throws GlobalException {

        Department result = null;
        try{
            result = departmentDao.queryObject(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }
        return result;
    }

    public void save(Department department) throws GlobalException {

        department.setDeleteFlag(false);
        department.setCreatedUser(CheckUtils.getAuthentication().getName());
        try{
            departmentDao.save(department);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void update(Department department) throws GlobalException {

        department.setModifiedUser(CheckUtils.getAuthentication().getName());
        try{
            departmentDao.update(department);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void deleteBatch(Long[] ids) throws GlobalException {

        try{
            departmentDao.deleteBatch(ids);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void delete(Long id) throws GlobalException {

        try{
            departmentDao.delete(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }
}
