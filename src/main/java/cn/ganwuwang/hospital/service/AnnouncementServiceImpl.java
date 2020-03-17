package cn.ganwuwang.hospital.service;

import cn.ganwuwang.hospital.dao.AnnouncementDao;
import cn.ganwuwang.hospital.domain.constant.ResultEnum;
import cn.ganwuwang.hospital.domain.pojo.Announcement;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.query.PageQuery;
import cn.ganwuwang.hospital.domain.query.Sort;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementServiceImpl {

    @Autowired
    private AnnouncementDao announcementDao;

    public List<Announcement> queryPageList(Page page, List<Sort> sort, Announcement announcement) throws GlobalException {

        List<Announcement> result = null;
        PageQuery query = new PageQuery(announcement);
        query.setPage(page);
        if(sort != null){
            query.setSort(sort);
        }

        try{
            result = announcementDao.queryList(query);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

        return result;
    }

    public Integer queryTotal(Announcement announcement) throws GlobalException {

        Integer result = 0;
        PageQuery query = new PageQuery(announcement);

        try{
            result = announcementDao.queryTotal(query);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

        return result;
    }

    public Announcement queryObject(Long id) throws GlobalException {

        Announcement result = null;
        try{
            result = announcementDao.queryObject(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }
        return result;
    }

    public void save(Announcement announcement) throws GlobalException {

        announcement.setDeleteFlag(false);
        announcement.setCreatedUser(CheckUtils.getAuthentication().getName());
        try{
            announcementDao.save(announcement);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void update(Announcement announcement) throws GlobalException {

        announcement.setModifiedUser(CheckUtils.getAuthentication().getName());
        try{
            announcementDao.update(announcement);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void deleteBatch(Long[] ids) throws GlobalException {

        try{
            announcementDao.deleteBatch(ids);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }

    public void delete(Long id) throws GlobalException {

        try{
            announcementDao.delete(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e, ResultEnum.DB_ERROR);
        }

    }
}
