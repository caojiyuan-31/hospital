package cn.ganwuwang.hospital.controller;

import cn.ganwuwang.hospital.domain.pojo.Department;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.domain.results.PageRes;
import cn.ganwuwang.hospital.domain.results.Result;
import cn.ganwuwang.hospital.service.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public Result getPageList() throws GlobalException {

        Page page = new Page(1,1000);
        List<Department> list = departmentService.queryPageList(page, null, null);
        int total = departmentService.queryTotal(null);
        PageRes pageRes = new PageRes(list, total, page);

        return new Result(pageRes);

    }

}
