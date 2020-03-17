package cn.ganwuwang.hospital.controller;

import cn.ganwuwang.hospital.domain.constant.ResultEnum;
import cn.ganwuwang.hospital.domain.pojo.Role;
import cn.ganwuwang.hospital.domain.pojo.User;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.service.RoleServiceImpl;
import cn.ganwuwang.hospital.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private UserServiceImpl userService;

    @ResponseBody
    @RequestMapping(value = "/string", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public String string(String in){
        return in;
    }

    @RequestMapping(value = "/exception", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public String exception() throws GlobalException {
        throw new GlobalException(ResultEnum.DB_ERROR);
    }

    @RequestMapping(value = "/exception2", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public String exception2() throws Exception {
        throw new Exception("sssss");
    }

    @ResponseBody
    @RequestMapping(value = "/get", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public Role get(Long id) throws Exception {
        return roleService.queryObject(id);
    }

    @ResponseBody
    @RequestMapping(value = "/save", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public String save(User user) throws Exception {
       userService.save(user);
       return "ok";
    }

}
