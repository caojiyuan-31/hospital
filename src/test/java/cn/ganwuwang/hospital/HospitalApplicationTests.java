package cn.ganwuwang.hospital;

import cn.ganwuwang.hospital.controller.DataController;
import cn.ganwuwang.hospital.controller.UserController;
import cn.ganwuwang.hospital.dao.DepartmentDao;
import cn.ganwuwang.hospital.dao.RoleDao;
import cn.ganwuwang.hospital.domain.pojo.Role;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.query.PageQuery;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.service.DataServiceImpl;
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
    private RedisTemplate redisTemplate;

    @Autowired
    UserController userController;

    @Autowired
    DataServiceImpl dataService;

    @Autowired
    DepartmentDao departmentDao;

    @Autowired
    DataController dataController;

    @Test
    void test() throws GlobalException {
        String text = "血压低打针心窝疼是怎么回事";
        System.out.println(dataController.forecastCategory(text).getData());
    }

    /*@Test
    void data() throws GlobalException {

        StringBuffer sb = new StringBuffer();
        String url = "/department/symptoms/44.html";
        Data d = new Data();
        d.setCategory("精神科");
        List<String> list = GetHtmlData.getZZForSymptoms(url);
        for(String o : list){
            d.setText(GetHtmlData.getChinese(o));
            dataService.save(d);
        }

        List<String> listUrl = GetHtmlData.getAllPage(url);
        for (String u : listUrl){
            list = GetHtmlData.getZZForSymptoms(u);
            for(String o : list){
                d.setText(GetHtmlData.getChinese(o));
                dataService.save(d);
            }
        }

    }*/

    @Test
    void queryListByCategory() throws GlobalException {
        //List<Data> data = dataService.queryListByCategory(null);
        /*for(Data o : data){
            System.out.println(o.getText());
        }*/
        System.out.println(dataService.queryTotalByCategory(null));
    }

    @Test
    void cache() throws GlobalException {
        System.out.println(userController.getInfo(new Long(2)));
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
