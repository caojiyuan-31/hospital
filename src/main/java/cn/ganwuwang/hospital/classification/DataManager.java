package cn.ganwuwang.hospital.classification;

import cn.ganwuwang.hospital.domain.pojo.Data;
import cn.ganwuwang.hospital.domain.pojo.Department;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.service.DataServiceImpl;
import cn.ganwuwang.hospital.service.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataManager {

    @Autowired
    private DataServiceImpl dataService;

    @Autowired
    private DepartmentServiceImpl departmentService;
    /**
     * 返回训练文本类别，这个类别就是科室名
     * @return 训练文本类别
     */
    @Cacheable(cacheNames = {"Category"})
    public List<Department> getCategory() throws GlobalException {
        return departmentService.getList();
    }

    /**
     * 返回训练文本集中所有的文本数目
     * @return 训练文本集中所有的文本数目
     */
    @Cacheable(cacheNames = {"DataCount"})
    public int getDataCount() throws GlobalException {
        return dataService.queryTotalByCategory(null);
    }
    /**
     * 返回训练文本集中在给定分类下的训练文本数目
     * @param category 给定的分类
     * @return 训练文本集中在给定分类下的训练文本数目
     */
    @Cacheable(cacheNames = {"DataCountOfCategory"})
    public int getDataCountOfCategory(String category) throws GlobalException {
        return dataService.queryTotalByCategory(category);
    }
    /**
     * 返回给定分类中包含关键词的训练文本的数目
     * @param category 给定的分类
     * @param key 给定的关键词
     * @return 给定分类中包含关键词的训练文本的数目
     */
    @Cacheable(cacheNames = {"CountContainKeyOfCategory"})
    public int getCountContainKeyOfCategory(String category,String key) throws GlobalException {
        int ret = 0;
        List<Data> list = dataService.queryListByCategory(category);
        for(Data d : list){
            if(d.getText().contains(key)){
                ret++;
            }
        }
        return ret;
    }

    /**
     * 返回包含关键词的训练文本的数目
     * @param key 给定的关键词
     * @return 包含关键词的训练文本的数目
     */
    @Cacheable(cacheNames = {"CountContainKey"})
    public int getCountContainKey(String key) throws GlobalException {
        int ret = 0;
        List<Data> list = dataService.queryListByCategory(null);
        for(Data d : list){
            if(d.getText().contains(key)){
                ret++;
            }
        }
        return ret;
    }

}
