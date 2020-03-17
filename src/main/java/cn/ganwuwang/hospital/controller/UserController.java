package cn.ganwuwang.hospital.controller;


import cn.ganwuwang.hospital.domain.constant.ResultEnum;
import cn.ganwuwang.hospital.domain.pojo.User;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.query.Sort;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.domain.results.PageRes;
import cn.ganwuwang.hospital.domain.results.Result;
import cn.ganwuwang.hospital.service.UserServiceImpl;
import cn.ganwuwang.hospital.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

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

    @RequestMapping(value = "/info", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.GET)
    @ResponseBody
    public Result getInfo(Long id) throws GlobalException {

        return new Result(userService.queryObject(id));

    }


    @RequestMapping(value = "/save", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    public Result saveUser(User user) throws GlobalException {

        if(CheckUtils.isEmptyBatch(user.getUsername(), user.getPassword(), user.getIdentity(), user.getEmail())
                || user.getPhone() == null){
            throw new GlobalException(ResultEnum.DATA_ERROR);
        }

        userService.save(user);
        return new Result();

    }

    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    public Result updateUser(User user) throws GlobalException {

        userService.update(user);
        return new Result();

    }

}
