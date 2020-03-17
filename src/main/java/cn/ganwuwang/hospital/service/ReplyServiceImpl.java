package cn.ganwuwang.hospital.service;

import cn.ganwuwang.hospital.dao.ReplyDao;
import cn.ganwuwang.hospital.domain.constant.ResultEnum;
import cn.ganwuwang.hospital.domain.pojo.Reply;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.query.PageQuery;
import cn.ganwuwang.hospital.domain.query.Sort;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImpl {

    @Autowired
    private ReplyDao replyDao;

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
}
