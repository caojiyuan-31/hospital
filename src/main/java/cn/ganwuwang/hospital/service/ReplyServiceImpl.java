package cn.ganwuwang.hospital.service;

import cn.ganwuwang.hospital.dao.ReplyDao;
import cn.ganwuwang.hospital.domain.constant.ResultEnum;
import cn.ganwuwang.hospital.domain.pojo.Doctor;
import cn.ganwuwang.hospital.domain.pojo.Reply;
import cn.ganwuwang.hospital.domain.pojo.ReplyTree;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.query.PageQuery;
import cn.ganwuwang.hospital.domain.query.Sort;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReplyServiceImpl {

    @Autowired
    private ReplyDao replyDao;

    @Autowired
    private DoctorServiceImpl doctorService;

    public List<Reply> queryPageList(Page page, List<Sort> sort, Reply reply) throws GlobalException {

        List<Reply> result = null;
        PageQuery query = new PageQuery(reply);
        query.setPage(page);
        if(sort != null){
            query.setSort(sort);
        }

        try{
            result = replyDao.queryList(query);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

        return result;
    }

    public Integer queryTotal(Reply reply) throws GlobalException {

        Integer result = 0;
        PageQuery query = new PageQuery(reply);

        try{
            result = replyDao.queryTotal(query);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

        return result;
    }

    public Reply queryObject(Long id) throws GlobalException {

        Reply result = null;
        try{
            result = replyDao.queryObject(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }
        return result;
    }

    public void save(Reply reply) throws GlobalException {

        reply.setDeleteFlag(false);
        reply.setCreatedUser(CheckUtils.getAuthentication().getName());
        try{
            replyDao.save(reply);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void update(Reply reply) throws GlobalException {

        reply.setModifiedUser(CheckUtils.getAuthentication().getName());
        try{
            replyDao.update(reply);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void deleteBatch(Long[] ids) throws GlobalException {

        try{
            replyDao.deleteBatch(ids);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void delete(Long id) throws GlobalException {

        try{
            replyDao.delete(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public Long getParentName(Long id) throws GlobalException {

        Long re = null;
        Reply reply = queryObject(getParent(id));
        Doctor d = new Doctor();
        d.setName(reply.getToName());
        re = doctorService.queryPageList(new Page(1, 10), null, d).get(0).getId();
        return re;
    }

    private Long getParent(Long id) throws GlobalException {

        Long re =id;
        Reply reply = queryObject(id);

        if(reply.getParentId() == 0){
            return re;
        }

        return  getParent(reply.getParentId());
    }


    public List<ReplyTree> getTree(Long parentId, String toName) throws GlobalException {
        List<ReplyTree> data = null;


        Reply r = new Reply();
        r.setToName(toName);
        r.setParentId(parentId);

        try{
            data = getchildrens(r);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

        return  data;
    }

    private List<ReplyTree> getchildrens(Reply r) throws GlobalException {


        //根节点的父code=0
        if(queryTotal(r) == 0){
            return null;
        }
        List<Reply> list = queryPageList(new Page(1,1000), null, r);
        List<ReplyTree> result = new ArrayList<ReplyTree>();
        for(Reply re : list){
            ReplyTree node = new ReplyTree();
            node.setId(re.getId());
            node.setLabel(re.getFromName()+" @ "+re.getToName()+"说:"+re.getText());
            node.setValue(re);
            r.setParentId(re.getId());
            r.setToName(null);
            node.setChildren(getchildrens(r));
            result.add(node);
        }
        return result;
    }
}
