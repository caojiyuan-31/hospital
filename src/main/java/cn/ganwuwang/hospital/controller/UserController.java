package cn.ganwuwang.hospital.controller;


import cn.ganwuwang.hospital.domain.constant.ResultEnum;
import cn.ganwuwang.hospital.domain.pojo.LoadUser;
import cn.ganwuwang.hospital.domain.pojo.User;
import cn.ganwuwang.hospital.domain.pojo.UserAndCheck;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.query.Sort;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.domain.results.PageRes;
import cn.ganwuwang.hospital.domain.results.Result;
import cn.ganwuwang.hospital.service.MailServiceImpl;
import cn.ganwuwang.hospital.service.UserServiceImpl;
import cn.ganwuwang.hospital.utils.CheckUtils;
import cn.ganwuwang.hospital.utils.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/current", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.GET)
    @ResponseBody
    public Result currentUser() {

        return new Result(CheckUtils.getAuthentication().getPrincipal());

    }

    @RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.GET)
    @ResponseBody
    public Result getPageList(Page page, List<Sort> sort, User user) throws GlobalException {

        List<User> list = userService.queryPageList(page, sort, user);
        int total = userService.queryTotal(user);
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
    public Result getInfo(Long id) throws GlobalException {

        return new Result(userService.queryObject(id));

    }

    @RequestMapping(value = "/sendCheckMail", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.POST)
    @ResponseBody
    public Result sendCheckMail(@RequestBody(required=false) String to) throws GlobalException {

        System.out.println(to);

        if(to == null || "".equals(to)){
            Object o = CheckUtils.getAuthentication().getPrincipal();
            LoadUser u = null;
            if(o instanceof LoadUser){
                u = (LoadUser)o;
            }else {
                throw new GlobalException(ResultEnum.LOAD_ERROR);
            }
            to = u.getEmail();
        }else {
            User q = new User();
            q.setEmail(to);
            if(userService.queryTotal(q) != 0){
                throw new GlobalException(ResultEnum.EMAIL_ERROR);
            }
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

        userService.save(user);
        return new Result();

    }

    @RequestMapping(value = "/updateOther", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    public Result updateSelfOther(@RequestBody UserAndCheck userAndCheck) throws GlobalException {

        User user = userAndCheck.getUser();
        Integer check = userAndCheck.getCheck();

        if(( user.getEmail() == null || "".equals(user.getEmail()))
                && (user.getPassword() == null || "".equals(user.getPassword()) )){
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

        if(!mailService.checkMail(user.getEmail(), check)){
            throw new GlobalException(ResultEnum.CHECK_ERROR);
        }

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
        if(u.getId() == null){
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
    public Result updateUser(User user) throws GlobalException {

        userService.update(user);
        return new Result();

    }

}
