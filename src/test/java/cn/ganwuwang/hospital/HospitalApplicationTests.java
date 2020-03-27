package cn.ganwuwang.hospital;

import cn.ganwuwang.hospital.classification.Different;
import cn.ganwuwang.hospital.controller.DataController;
import cn.ganwuwang.hospital.controller.UserController;
import cn.ganwuwang.hospital.dao.*;
import cn.ganwuwang.hospital.domain.pojo.*;
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

    @Autowired
    Different different;

    @Autowired
    DoctorDao doctorDao;

    @Autowired
    UserDao userDao;

    @Autowired
    UserRoleDao userRoleDao;

    @Autowired
    AnnouncementDao announcementDao;

    @Test
    void test() throws GlobalException, InterruptedException {

        /*String name = "医生";
        Long phone = 13131234567L;
        Long identity = 130602111111110000L;
        User u = new User();
        u.setDeleteFlag(false);
        u.setCreatedUser("SYSTEM");
        u.setPassword("$2a$10$KNAwdUjvoD1cy6q7vn7m3eWPGW/ecly8QHNdWsTOr0gfT9NrwcM7a");

        Doctor d = new Doctor();
        d.setCreatedUser("SYSTEM");
        d.setSchoolName("XXX大学硕士");
        d.setDepartmentId(new Long(8));
        d.setDepartmentName("精神科");
        d.setLevel("普通");
        d.setAm(3);
        d.setPm(3);
        d.setSkill("擅长精神科");
        d.setDescription("以硕士学位毕业于XXX大学，擅长精神科");
        d.setUrl("http://localhost:8080/upload/ys.jpg");
        d.setDeleteFlag(false);
        for(int i = 35 ; i < 40 ; i++){
            u.setUsername(name + i);
            u.setName(name + i);
            u.setPhone(phone + i);
            u.setIdentity(String.valueOf(identity+i));
            u.setEmail(i + "@qq.com");
            d.setName(name + i);
            userDao.save(u);
            d.setUserId(u.getId());
            doctorDao.save(d);
        }*/

        /*UserRole userRole = new UserRole();
        userRole.setCreatedUser("SYSTEM");
        userRole.setDeleteFlag(false);

        for(int i = 14; i < 55 ; i++){
            userRole.setRoleId(new Long(1));
            userRole.setUserId((long)i);
            userRoleDao.save(userRole);
            userRole.setRoleId(new Long(2));
            userRoleDao.save(userRole);
        }*/

        String title = "公告";
        String text = "内容";
        Announcement a = new Announcement();
        a.setCreatedUser("SYSTEM");
        a.setDeleteFlag(false);

        for(int i =0 ; i<100 ; i++){
            a.setTitle(title + i);
            a.setText(text + i);
            announcementDao.save(a);
            Thread.sleep(1000);
        }

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
