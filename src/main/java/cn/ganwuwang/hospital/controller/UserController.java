package cn.ganwuwang.hospital.controller;


import cn.ganwuwang.hospital.domain.constant.ResultEnum;
import cn.ganwuwang.hospital.domain.pojo.Doctor;
import cn.ganwuwang.hospital.domain.pojo.LoadUser;
import cn.ganwuwang.hospital.domain.pojo.User;
import cn.ganwuwang.hospital.domain.pojo.UserAndCheck;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.domain.results.PageRes;
import cn.ganwuwang.hospital.domain.results.Result;
import cn.ganwuwang.hospital.service.DoctorServiceImpl;
import cn.ganwuwang.hospital.service.MailServiceImpl;
import cn.ganwuwang.hospital.service.UserServiceImpl;
import cn.ganwuwang.hospital.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private MailServiceImpl mailService;

    @Autowired
    private DoctorServiceImpl doctorService;

    @RequestMapping(value = "/current", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.GET)
    @ResponseBody
    public Result currentUser() {

        return new Result(CheckUtils.getAuthentication().getPrincipal());

    }

    @RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.GET)
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public Result getPageList(Integer pageNo, Integer pageSize, String name) throws GlobalException {

        Page page = new Page(pageNo, pageSize);
        User user = new User();
        user.setUsername(name);

        List<User> list = userService.queryPageListByName(page, null, user);
        int total = userService.queryTotalByName(user);
        PageRes pageRes = new PageRes(list, total, page);

        return new Result(pageRes);

    }

    @RequestMapping(value = "/getSelfInfo", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.GET)
    @ResponseBody
    public Result getSelfInfo() throws GlobalException {

        Object o = CheckUtils.getAuthentication().getPrincipal();
        LoadUser u = null;
        if(o instanceof LoadUser){
            u = (LoadUser)o;
        }else {
            throw new GlobalException(ResultEnum.LOAD_ERROR);
        }
        if(u.getId() == null){
            throw new GlobalException(ResultEnum.LOAD_ERROR);
        }
        return new Result(userService.queryObject(u.getId()));

    }

    @RequestMapping(value = "/info", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.GET)
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public Result getInfo(Long id) throws GlobalException {

        return new Result(userService.queryObject(id));

    }

    @RequestMapping(value = "/sendCheckMail", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.POST)
    @ResponseBody
    public Result sendCheckMail(@RequestBody(required=false) String to) throws GlobalException {

        if(to == null || "".equals(to)){
            Object o = CheckUtils.getAuthentication().getPrincipal();
            LoadUser u = null;
            if(o instanceof LoadUser){
                u = (LoadUser)o;
            }else {
                throw new GlobalException(ResultEnum.LOAD_ERROR);
            }
            to = u.getEmail();
        }

        mailService.sendCheckMail(to);
        return new Result();

    }

    @RequestMapping(value = "/save", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    public Result saveUser(@RequestBody UserAndCheck userAndCheck) throws GlobalException {

        User user = userAndCheck.getUser();
        Integer check = userAndCheck.getCheck();

        if(CheckUtils.isEmptyBatch(user.getUsername(), user.getPassword(), user.getEmail())){
            throw new GlobalException(ResultEnum.DATA_ERROR);
        }

        if(!mailService.checkMail(user.getEmail(), check)){
            throw new GlobalException(ResultEnum.CHECK_ERROR);
        }

        User q = new User();
        q.setUsername(user.getUsername());
        if(userService.queryTotal(q) != 0){
            throw new GlobalException(ResultEnum.NAME_ERROR);
        }

        q.setUsername(null);
        q.setEmail(user.getEmail());
        if(userService.queryTotal(q) != 0){
            throw new GlobalException(ResultEnum.EMAIL_ERROR);
        }

        userService.save(user);
        return new Result();

    }

    @RequestMapping(value = "/updateSelfEmail", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    public Result updateSelfEmail(@RequestBody UserAndCheck userAndCheck) throws GlobalException {

        User user = userAndCheck.getUser();
        Integer check = userAndCheck.getCheck();

        if( user.getEmail() == null || "".equals(user.getEmail())){
            throw new GlobalException(ResultEnum.DATA_ERROR);
        }

        Object o = CheckUtils.getAuthentication().getPrincipal();
        LoadUser u = null;
        if(o instanceof LoadUser){
            u = (LoadUser)o;
        }else {
            throw new GlobalException(ResultEnum.LOAD_ERROR);
        }
        user.setId(u.getId());


        User q = new User();
        q.setEmail(user.getEmail());
        if(userService.queryTotal(q) != 0){
            throw new GlobalException(ResultEnum.EMAIL_ERROR);
        }

        if(!mailService.checkMail(user.getEmail(), check)){
            throw new GlobalException(ResultEnum.CHECK_ERROR);
        }

        userService.update(user);
        return new Result();

    }

    @RequestMapping(value = "/updatePassOfEmail", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    public Result updatePassOfEmail(@RequestBody UserAndCheck userAndCheck) throws GlobalException {

        User user = userAndCheck.getUser();
        Integer check = userAndCheck.getCheck();

        if(( user.getEmail() == null || "".equals(user.getEmail()))
                && (user.getPassword() == null || "".equals(user.getPassword()) )){
            throw new GlobalException(ResultEnum.DATA_ERROR);
        }

        User q = new User();
        q.setEmail(user.getEmail());
        if(userService.queryTotal(q) != 1){
            throw new GlobalException(ResultEnum.UNEMAIL_ERROR);
        }

        if(!mailService.checkMail(user.getEmail(), check)){
            throw new GlobalException(ResultEnum.CHECK_ERROR);
        }

        User OldUser = userService.queryPageList(new Page(1, 10), null, q).get(0);
        user.setId(OldUser.getId());

        userService.update(user);
        return new Result();

    }

    @RequestMapping(value = "/updateSelf", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    public Result updateSelf(@RequestBody User user) throws GlobalException {

        Object o = CheckUtils.getAuthentication().getPrincipal();
        LoadUser u = null;
        if(o instanceof LoadUser){
            u = (LoadUser)o;
        }else {
            throw new GlobalException(ResultEnum.LOAD_ERROR);
        }
        user.setId(u.getId());

        User q = new User();
        q.setIdentity(user.getIdentity());
        if(userService.queryTotal(q) != 0){
            throw new GlobalException(ResultEnum.IDENTITY_ERROR);
        }

        userService.update(user);
        return new Result();

    }

    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public Result updateUser(User user) throws GlobalException {

        userService.update(user);
        return new Result();

    }

    @RequestMapping(value = "/infoOfDoctorId", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.GET)
    @ResponseBody
    public Result getInfoOfDoctorId(Long id) throws GlobalException {

        Doctor d = doctorService.queryObject(id);
        User u = userService.queryObject(d.getUserId());

        return new Result(u.getUsername());

    }

}
