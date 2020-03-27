package cn.ganwuwang.hospital.controller;

import cn.ganwuwang.hospital.domain.pojo.Announcement;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.query.Sort;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.domain.results.PageRes;
import cn.ganwuwang.hospital.domain.results.Result;
import cn.ganwuwang.hospital.service.AnnouncementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

}
