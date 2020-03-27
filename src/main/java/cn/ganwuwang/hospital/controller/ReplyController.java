package cn.ganwuwang.hospital.controller;

import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.domain.results.Result;
import cn.ganwuwang.hospital.service.ReplyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private ReplyServiceImpl replyService;

    @RequestMapping(value = "/tree", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.GET)
    @ResponseBody
    public Result getTree(Long toId, Long fromId) throws GlobalException {

        System.out.println(toId);
        System.out.println(fromId);

        return new Result(replyService.getTree(new Long(0), toId, fromId));

    }


}
