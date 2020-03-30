package cn.ganwuwang.hospital.controller;

import cn.ganwuwang.hospital.domain.pojo.Department;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.domain.results.PageRes;
import cn.ganwuwang.hospital.domain.results.Result;
import cn.ganwuwang.hospital.service.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentServiceImpl departmentService;

    @RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.GET)
    @ResponseBody
    public Result getPageList(Integer pageNo, Integer pageSize) throws GlobalException {

        Page page = new Page(pageNo,pageSize);
        List<Department> list = departmentService.queryPageList(page, null, null);
        int total = departmentService.queryTotal(null);
        PageRes pageRes = new PageRes(list, total, page);

        return new Result(pageRes);

    }

    @RequestMapping(value = "/info", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.GET)
    @ResponseBody
    public Result getInfo(Long id) throws GlobalException {

        return new Result(departmentService.queryObject(id));

    }

    @RequestMapping(value = "/save", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public Result save(@RequestBody Department department) throws GlobalException {

        departmentService.save(department);
        return new Result();

    }

    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public Result update(@RequestBody Department department) throws GlobalException {

        departmentService.update(department);
        return new Result();

    }

    @RequestMapping(value = "/delete", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public Result delete(@RequestBody Long id) throws GlobalException {

        departmentService.delete(id);
        return new Result();

    }

}
