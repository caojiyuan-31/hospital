package cn.ganwuwang.hospital.service;

import cn.ganwuwang.hospital.dao.DoctorDao;
import cn.ganwuwang.hospital.domain.constant.ResultEnum;
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
public class DoctorServiceImpl {

    @Autowired
    private DoctorDao doctorDao;

    public List<Doctor> queryPageList(Page page, List<Sort> sort, Doctor doctor) throws GlobalException {

        List<Doctor> result = null;
        PageQuery query = new PageQuery(doctor);
        query.setPage(page);
        if(sort != null){
            query.setSort(sort);
        }

        try{
            result = doctorDao.queryList(query);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

        return result;
    }

    public Integer queryTotal(Doctor doctor) throws GlobalException {

        Integer result = 0;
        PageQuery query = new PageQuery(doctor);

        try{
            result = doctorDao.queryTotal(query);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

        return result;
    }

    public Doctor queryObject(Long id) throws GlobalException {

        Doctor result = null;
        try{
            result = doctorDao.queryObject(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }
        return result;
    }

    public void save(Doctor doctor) throws GlobalException {

        doctor.setDeleteFlag(false);
        doctor.setCreatedUser(CheckUtils.getAuthentication().getName());
        try{
            doctorDao.save(doctor);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void update(Doctor doctor) throws GlobalException {

        doctor.setModifiedUser(CheckUtils.getAuthentication().getName());
        try{
            doctorDao.update(doctor);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void deleteBatch(Long[] ids) throws GlobalException {

        try{
            doctorDao.deleteBatch(ids);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void delete(Long id) throws GlobalException {

        try{
            doctorDao.delete(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }
}
