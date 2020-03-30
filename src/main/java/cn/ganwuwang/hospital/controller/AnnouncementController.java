package cn.ganwuwang.hospital.controller;

import cn.ganwuwang.hospital.domain.pojo.Announcement;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.query.Sort;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.domain.results.PageRes;
import cn.ganwuwang.hospital.domain.results.Result;
import cn.ganwuwang.hospital.service.AnnouncementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementServiceImpl announcementService;

    @RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    @ResponseBody
    public Result getPageList(Integer pageNo, Integer pageSize) throws GlobalException {

        Page page = new Page(pageNo, pageSize);
        List<Sort> sort = new ArrayList<Sort>();
        Sort s = new Sort(Sort.Direction.DESC, "created_Time");
        sort.add(s);

        List<Announcement> list = announcementService.queryPageList(page, sort, null);
        int total = announcementService.queryTotal(null);
        PageRes pageRes = new PageRes(list, total, page);

        return new Result(pageRes);

    }

    @RequestMapping(value = "/save", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public Result save(@RequestBody Announcement announcement) throws GlobalException {

        announcementService.save(announcement);
        return new Result();

    }

    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public Result update(@RequestBody Announcement announcement) throws GlobalException {

        announcementService.update(announcement);
        return new Result();

    }

    @RequestMapping(value = "/delete", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public Result delete(@RequestBody Long id) throws GlobalException {

        announcementService.delete(id);
        return new Result();

    }

}
