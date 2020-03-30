package cn.ganwuwang.hospital.controller;

import cn.ganwuwang.hospital.domain.constant.ResultEnum;
import cn.ganwuwang.hospital.domain.pojo.LoadUser;
import cn.ganwuwang.hospital.domain.pojo.Reply;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.query.Sort;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.domain.results.PageRes;
import cn.ganwuwang.hospital.domain.results.Result;
import cn.ganwuwang.hospital.service.ReplyServiceImpl;
import cn.ganwuwang.hospital.utils.CheckUtils;
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
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private ReplyServiceImpl replyService;

    @RequestMapping(value = "/tree", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.GET)
    @ResponseBody
    public Result getTree(String toName) throws GlobalException {

        return new Result(replyService.getTree(new Long(0), toName));

    }

    @RequestMapping(value = "/doctorId", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.GET)
    @ResponseBody
    @Secured("ROLE_USER")
    public Result getParentName(Long id) throws GlobalException {

        return new Result(replyService.getParentName(id));

    }

    @RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    @ResponseBody
    @Secured("ROLE_USER")
    public Result getPageList(Integer pageNo, Integer pageSize, String toName) throws GlobalException {

        Page page = new Page(pageNo, pageSize);
        List<Sort> sort = new ArrayList<Sort>();
        Sort s = new Sort(Sort.Direction.DESC, "created_Time");
        sort.add(s);

        Reply reply = new Reply();
        reply.setToName(toName);

        List<Reply> list = replyService.queryPageList(page, sort, reply);
        int total = replyService.queryTotal(reply);
        PageRes pageRes = new PageRes(list, total, page);

        return new Result(pageRes);

    }


    @RequestMapping(value = "/save", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    @Secured("ROLE_USER")
    public Result saveUser(@RequestBody Reply reply) throws GlobalException {

        if(CheckUtils.isEmptyBatch(reply.getToName(), reply.getText()) || reply.getParentId() == null){
            throw new GlobalException(ResultEnum.DATA_ERROR);
        }

        Object o = CheckUtils.getAuthentication().getPrincipal();
        LoadUser u = null;
        if(o instanceof LoadUser){
            u = (LoadUser)o;
        }else {
            throw new GlobalException(ResultEnum.LOAD_ERROR);
        }

        if(u.getUsername().equals(reply.getToName())){
            throw new GlobalException(ResultEnum.REPLY_SELF);
        }

        reply.setFromName(u.getUsername());

        replyService.save(reply);
        return new Result();

    }

}
