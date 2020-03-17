package cn.ganwuwang.hospital;

import cn.ganwuwang.hospital.dao.RoleDao;
import cn.ganwuwang.hospital.domain.pojo.Role;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.query.PageQuery;
import cn.ganwuwang.hospital.utils.GsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class HospitalApplicationTests {

    @Autowired
    RoleDao roleDao;


    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void redis() {
        ValueOperations<String, String> o = redisTemplate.opsForValue();
        o.set("two","2");
        System.out.println(o.get("two"));
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
