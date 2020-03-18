package cn.ganwuwang.hospital;

import cn.ganwuwang.hospital.classification.Probability;
import cn.ganwuwang.hospital.classification.different;
import cn.ganwuwang.hospital.controller.UserController;
import cn.ganwuwang.hospital.dao.RoleDao;
import cn.ganwuwang.hospital.domain.pojo.Role;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.query.PageQuery;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.utils.GsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class HospitalApplicationTests {

    @Autowired
    RoleDao roleDao;

    @Autowired
    Probability probability;

    @Autowired
    different different;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    UserController userController;

    @Test
    void cache() throws GlobalException {
        System.out.println(userController.getInfo(new Long(2)));
    }

    @Test
    void probability() {
        System.out.println(probability.classify("明天三里屯见瑞丽服饰美容：2011瑞丽造型大赏派对瑞丽专属模特黄美熙康猴猴康乐帕丽扎提也会参加哦瑞丽专属模特转发(53)评论(5)12月8日17:10来自新浪微博"));
    }

    @Test
    void different() throws Exception {
        different.differentCount();
        //System.out.println(different.differentOfClassification("校园"));
    }

    @Test
    void setRedis() {
        redisTemplate.opsForValue().set("two","2");
    }

    @Test
    void getRedis() {
        System.out.println(redisTemplate.opsForValue().get("two"));
    }

    @Test
    void save() {
        Role r = new Role();
        r.setName("user");
        r.setDescription("普通用户");
        System.out.println(roleDao.save(r));
    }

    @Test
    void find() {
        System.out.println("+++++++++++++++++++++++++" + GsonUtils.toJson(roleDao.queryObject(1)));
        Role r = new Role();
        r.setName("user");
        PageQuery query = new PageQuery(r);
        query.setPage(new Page());
        System.out.println("+++++++++++++++++++++++++" + GsonUtils.toJson(roleDao.queryTotal(query)));
        System.out.println("+++++++++++++++++++++++++" + GsonUtils.toJson(roleDao.queryList(query)));
    }

    @Test
    void update() {
        Role r = new Role();
        r.setName("user");
        PageQuery query = new PageQuery(r);
        query.setPage(new Page());
        System.out.println("+++++++++++++++++++++++++" + GsonUtils.toJson(roleDao.queryTotal(query)));
        System.out.println("+++++++++++++++++++++++++" + GsonUtils.toJson(roleDao.delete(1)));
        System.out.println("+++++++++++++++++++++++++" + GsonUtils.toJson(roleDao.queryTotal(query)));
        r.setId(new Long(1));
        r.setDeleteFlag(false);
        System.out.println("+++++++++++++++++++++++++" + GsonUtils.toJson(roleDao.update(r)));
        r.setId(null);
        System.out.println("+++++++++++++++++++++++++" + GsonUtils.toJson(roleDao.queryTotal(query)));
        Long[] ids = new Long[1];
        ids[0] = new Long(1);
        System.out.println("+++++++++++++++++++++++++" + GsonUtils.toJson(roleDao.deleteBatch(ids)));
        System.out.println("+++++++++++++++++++++++++" + GsonUtils.toJson(roleDao.queryTotal(query)));
    }

}
