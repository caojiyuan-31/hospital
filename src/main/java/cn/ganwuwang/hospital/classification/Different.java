package cn.ganwuwang.hospital.classification;

import cn.ganwuwang.hospital.controller.DataController;
import cn.ganwuwang.hospital.domain.pojo.Data;
import cn.ganwuwang.hospital.domain.pojo.Department;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.service.DataServiceImpl;
import cn.ganwuwang.hospital.service.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class Different {

    private static final int SUM = 100;

    @Autowired
    private DataController dataController;

    @Autowired
    private DataServiceImpl dataService;

    @Autowired
    private DepartmentServiceImpl departmentService;


    public int differentCountOfCategory(String category) throws GlobalException {
        Random random = new Random();//默认构造方法
        List<Data>  list = dataService.queryListByCategory(category);
        int re = 0;
        for(int i = 0 ; i < SUM ; i++){
            StringBuffer sb = new StringBuffer();
            for(int j = 0 ; j < 3 ; j++){
                sb.append(list.get(random.nextInt(list.size())).getText() + " ");
            }
            String text = sb.toString();
            System.out.println(text);
            String r = (String) dataController.forecastCategory(text).getData();
            System.out.println();
            if(!category.equals(r)){
                re++;
            }
        }
        System.out.println(category+"分类对比完成，结果不同数量为："+re+" 错误率为："+(double)re/SUM);

        return re;
    }

    public void differentCount() throws GlobalException {
        int re = 0;
        List<Department> list = departmentService.getList();
        for(Department d : list){
            re += differentCountOfCategory(d.getName());
        }
        System.out.println("全部分类对比完成，结果不同数量为："+re+" 错误率为："+(double)re/(100*list.size()));
    }

}
