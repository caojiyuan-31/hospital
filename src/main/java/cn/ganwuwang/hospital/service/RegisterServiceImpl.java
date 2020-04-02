package cn.ganwuwang.hospital.service;

import cn.ganwuwang.hospital.dao.RegisterDao;
import cn.ganwuwang.hospital.domain.constant.ResultEnum;
import cn.ganwuwang.hospital.domain.constant.StatusEnum;
import cn.ganwuwang.hospital.domain.pojo.Doctor;
import cn.ganwuwang.hospital.domain.pojo.Register;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.query.PageQuery;
import cn.ganwuwang.hospital.domain.query.Sort;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegisterServiceImpl {

    @Autowired
    private RegisterDao registerDao;

    @Autowired
    private DoctorServiceImpl doctorService;

    @Autowired
    private MailServiceImpl mailService;

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

    @Transactional
    public void save(String to, Register register) throws GlobalException {

        Doctor doctor = doctorService.queryObject(register.getDoctorId());
        if(doctor == null){
            throw  new GlobalException(ResultEnum.DATA_ERROR);
        }

        Register q = new Register();
        q.setStatus(StatusEnum.ON_REGISTER.getStatus());
        q.setUserId(register.getUserId());
        if(queryTotal(q) != 0 ){
            throw new GlobalException(ResultEnum.REGISTER_TWO);
        }
        q.setUserId(null);
        q.setDoctorId(register.getDoctorId());
        q.setScope(register.getScope());

        q.setDate(register.getDate());
        Integer count = 0;
        if(register.getScope() == 0){
            count = doctor.getAm();
        }else {
            count = doctor.getPm();
        }

        if(queryTotal(q) >= count){
            throw new GlobalException(ResultEnum.REGISTER_FULL);
        }else {
            register.setDoctorName(doctor.getName());
            register.setDeleteFlag(false);
            register.setCreatedUser(CheckUtils.getAuthentication().getName());
            try{
                registerDao.save(register);
                Integer num = queryTotal(q);
                if(num > count){
                    throw new GlobalException(ResultEnum.REGISTER_FULL);
                }
                mailService.sendRegister(to, register, num);

            }catch (Exception e){
                e.printStackTrace();
                throw new GlobalException(e, ResultEnum.DB_ERROR);
            }
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
