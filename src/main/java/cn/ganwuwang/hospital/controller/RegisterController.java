package cn.ganwuwang.hospital.controller;

import cn.ganwuwang.hospital.domain.constant.ResultEnum;
import cn.ganwuwang.hospital.domain.constant.StatusEnum;
import cn.ganwuwang.hospital.domain.pojo.*;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.query.Sort;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.domain.results.PageRes;
import cn.ganwuwang.hospital.domain.results.Result;
import cn.ganwuwang.hospital.service.RegisterServiceImpl;
import cn.ganwuwang.hospital.service.UserServiceImpl;
import cn.ganwuwang.hospital.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterServiceImpl registerService;

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/num", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.GET)
    @ResponseBody
    @Secured("ROLE_USER")
    public Result getNum(Long userId, Long doctorId, @DateTimeFormat(pattern = "yyyy-MM-dd")Date date, Integer scope) throws GlobalException {

        Object o = CheckUtils.getAuthentication().getPrincipal();
        LoadUser u = null;
        if(o instanceof LoadUser){
            u = (LoadUser)o;
        }else {
            throw new GlobalException(ResultEnum.LOAD_ERROR);
        }
        Register q = new Register();
        q.setUserId(u.getId());
        q.setStatus(StatusEnum.ON_REGISTER.getStatus());
        List<Register> list =  registerService.queryPageList(new Page(1,20), null, q);
        if(list.size() == 0){
            throw new GlobalException(ResultEnum.REGISTER_NOT);
        }
        Register register = list.get(0);

        List<Sort> sort = new ArrayList<Sort>();
        Sort s = new Sort(Sort.Direction.ASC, "created_time");
        sort.add(s);
        Register status = new Register();
        status.setStatus(StatusEnum.ON_REGISTER.getStatus());
        status.setScope(register.getScope());
        status.setDate(register.getDate());
        status.setDoctorId(register.getDoctorId());
        List<Register> list2 =  registerService.queryPageList(new Page(1,20), sort, status);
        Integer re = 0;
        for(Register r : list2){
            re++;
            if(r.getUserId().equals(u.getId())){
                break;
            }
        }

        return new Result(re);
    }

    @RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.GET)
    @ResponseBody
    @Secured("ROLE_USER")
    public Result getPageList(Integer pageNo, Integer pageSize,
                              Long userId, Long doctorId, @DateTimeFormat(pattern = "yyyy-MM-dd")Date date, Integer scope, Integer status) throws GlobalException {

        Page page = new Page(pageNo, pageSize);
        List<Sort> sort = new ArrayList<Sort>();
        Sort s = new Sort(Sort.Direction.DESC, "date");
        sort.add(s);
        Register register = new Register();
        register.setUserId(userId);
        register.setDoctorId(doctorId);
        register.setDate(date);
        register.setScope(scope);
        register.setStatus(status);
        List<Register> list = registerService.queryPageList(page, sort, register);
        int total = registerService.queryTotal(register);
        PageRes pageRes = new PageRes(list, total, page);

        return new Result(pageRes);

    }

    @RequestMapping(value = "/save", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    @Secured("ROLE_USER")
    public Result saveRegister(@RequestBody Register register) throws GlobalException {

        if(register.getDoctorId() == null || register.getDate() == null || register.getScope() == null
           ||  CheckUtils.isEmptyBatch(register.getText())){
            throw  new GlobalException(ResultEnum.DATA_ERROR);
        }

        Object o = CheckUtils.getAuthentication().getPrincipal();
        LoadUser u = null;
        if(o instanceof LoadUser){
            u = (LoadUser)o;
        }else {
            throw new GlobalException(ResultEnum.LOAD_ERROR);
        }
        User user = userService.queryObject(u.getId());
        if(user.getPhone() == null ||
           CheckUtils.isEmptyBatch(user.getName(), user.getIdentity()) ){
            throw new GlobalException(ResultEnum.IDENTITY_UNKNOW);
        }

        register.setUserId(u.getId());
        register.setUserName(u.getUsername());
        register.setStatus(StatusEnum.ON_REGISTER.getStatus());
        registerService.save(u.getEmail(), register);

        return new Result();

    }


    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    @Secured("ROLE_DOCTOR")
    public Result updateRegister(@RequestBody Register register) throws GlobalException {

        registerService.update(register);
        return new Result();

    }

    @RequestMapping(value = "/cancel", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    @Secured("ROLE_USER")
    public Result cancelStatus() throws GlobalException {

        Object o = CheckUtils.getAuthentication().getPrincipal();
        LoadUser u = null;
        if(o instanceof LoadUser){
            u = (LoadUser)o;
        }else {
            throw new GlobalException(ResultEnum.LOAD_ERROR);
        }
        Register q = new Register();
        q.setUserId(u.getId());
        q.setStatus(StatusEnum.ON_REGISTER.getStatus());
        List<Register> list =  registerService.queryPageList(new Page(1,20), null, q);
        if(list.size() == 0){
            throw new GlobalException(ResultEnum.REGISTER_NOT);
        }
        Register register = list.get(0);

        Date now = new Date();
        if(register.getDate().getTime() <= now.getTime() ){
            throw new GlobalException(ResultEnum.REGISTER_CANNEL);
        }

        register.setStatus(StatusEnum.CANCEL_REGISTER.getStatus());

        registerService.update(register);
        return new Result();

    }

}
