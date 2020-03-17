package cn.ganwuwang.hospital.controller;


import cn.ganwuwang.hospital.domain.constant.ResultEnum;
import cn.ganwuwang.hospital.domain.pojo.UserRole;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.query.Sort;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.domain.results.PageRes;
import cn.ganwuwang.hospital.domain.results.Result;
import cn.ganwuwang.hospital.service.UserRoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/userRole")
public class UserRoleController {

    @Autowired
    private UserRoleServiceImpl userRoleService;

    @RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.GET)
    @ResponseBody
    public Result getPageList(Page page, List<Sort> sort, UserRole userRole) throws GlobalException {

        List<UserRole> list = userRoleService.queryPageList(page, sort, userRole);
        int total = userRoleService.queryTotal(userRole);
        PageRes pageRes = new PageRes(list, total, page);

        return new Result(pageRes);

    }

    @RequestMapping(value = "/info", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.GET)
    @ResponseBody
    public Result getInfo(Long id) throws GlobalException {

        return new Result(userRoleService.queryObject(id));

    }


    @RequestMapping(value = "/save", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    public Result saveUserRole(UserRole userRole) throws GlobalException {

        if(userRole.getRoleId() == null || userRole.getUserId() == null){
            throw new GlobalException(ResultEnum.DATA_ERROR);
        }

        userRoleService.save(userRole);
        return new Result();

    }

    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    public Result updateUserRole(UserRole userRole) throws GlobalException {

        userRoleService.update(userRole);
        return new Result();

    }

    @RequestMapping(value = "/delete", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    public Result deleteUserRole(Long id) throws GlobalException {

        userRoleService.delete(id);
        return new Result();

    }

    @RequestMapping(value = "/deleteBatch", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    public Result deleteBatchUserRole(Long[] ids) throws GlobalException {

        userRoleService.deleteBatch(ids);
        return new Result();

    }

}
