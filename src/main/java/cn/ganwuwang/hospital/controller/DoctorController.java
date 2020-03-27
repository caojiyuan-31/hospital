package cn.ganwuwang.hospital.controller;

import cn.ganwuwang.hospital.domain.pojo.Doctor;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.domain.results.PageRes;
import cn.ganwuwang.hospital.domain.results.Result;
import cn.ganwuwang.hospital.service.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

}
