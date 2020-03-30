package cn.ganwuwang.hospital.controller;

import cn.ganwuwang.hospital.domain.constant.ResultEnum;
import cn.ganwuwang.hospital.domain.pojo.Doctor;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.domain.results.PageRes;
import cn.ganwuwang.hospital.domain.results.Result;
import cn.ganwuwang.hospital.service.DoctorServiceImpl;
import cn.ganwuwang.hospital.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorServiceImpl doctorService;

    @RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    @ResponseBody
    public Result getPageList(Integer pageNo, Integer pageSize, Long departmentId, String level) throws GlobalException {

        Page page = new Page(pageNo, pageSize);
        Doctor doctor = new Doctor();
        doctor.setDepartmentId(departmentId);
        doctor.setLevel(level);

        List<Doctor> list = doctorService.queryPageList(page, null, doctor);
        int total = doctorService.queryTotal(doctor);
        PageRes pageRes = new PageRes(list, total, page);

        return new Result(pageRes);

    }

    @RequestMapping(value = "/info", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.GET)
    @ResponseBody
    public Result getInfo(Long id) throws GlobalException {

        return new Result(doctorService.queryObject(id));

    }

    @RequestMapping(value = "/infoOfUserId", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.GET)
    @ResponseBody
    public Result getInfoOfUserId(Long id) throws GlobalException {

        Doctor d = new Doctor();
        d.setUserId(id);
        List<Doctor> list = doctorService.queryPageList(new Page(1, 20), null, d);
        if(list.size() == 0){
            throw new GlobalException(ResultEnum.DATA_ERROR);
        }

        return new Result(list.get(0));

    }

    @RequestMapping(value = "/save", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public Result save(@RequestBody Doctor doctor) throws GlobalException {

        if(CheckUtils.isEmptyBatch(doctor.getName(), doctor.getLevel(), doctor.getDepartmentName(), doctor.getUrl())
        || doctor.getUserId() == null || doctor.getAm() == null || doctor.getPm() == null || doctor.getDepartmentId() == null){
            throw new GlobalException(ResultEnum.DATA_ERROR);
        }

        Doctor q = new Doctor();
        q.setUserId(doctor.getUserId());
        if(doctorService.queryTotal(q) != 0){
            throw new GlobalException(ResultEnum.DATA_ERROR);
        }

        doctorService.save(doctor);
        return new Result();

    }


    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public Result update(@RequestBody Doctor doctor) throws GlobalException {

        doctorService.update(doctor);
        return new Result();

    }

    @RequestMapping(value = "/delete", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public Result delete(@RequestBody Long id) throws GlobalException {

        doctorService.delete(id);
        return new Result();

    }

}
