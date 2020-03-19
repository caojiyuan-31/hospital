package cn.ganwuwang.hospital.controller;

import cn.ganwuwang.hospital.classification.*;
import cn.ganwuwang.hospital.domain.constant.ResultEnum;
import cn.ganwuwang.hospital.domain.pojo.Department;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.domain.results.Result;
import cn.ganwuwang.hospital.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/data")
public class DataController {

    @Autowired
    private ProbabilityServiceImpl probabilityService;

    @Autowired
    private DataManager dm;

    @RequestMapping(value = "/forecastCategory", produces = {"application/json;charset=UTF-8"},  method = RequestMethod.POST)
    @ResponseBody
    public Result forecastCategory(String text) throws GlobalException {

        if(CheckUtils.isEmptyBatch(text)){
            return new Result(ResultEnum.DATA_ERROR);
        }

        return new Result(classify(text));

    }

    /**
     * 计算给定的文本属性向量X在给定的分类Cj中的类条件概率
     * <code>ClassConditionalProbability</code>连乘值
     * @param X 给定的文本属性向量
     * @param Cj 给定的类别
     * @return 分类条件概率连乘值，即<br>
     */
    private float calcProd(String[] X, String Cj) throws GlobalException {
        float ret = 1.0F;
        // 类条件概率连乘
        for (int i = 0; i <X.length; i++)
        {
            String Xi = X[i];
            //因为结果过小，因此在连乘之前放大10倍，这对最终结果并无影响，因为我们只是比较概率大小而已
            //ret *= (calculatePxc(Xi, Cj) / calculatePx(Xi));
            ret *= probabilityService.calculatePxc(Xi, Cj)*100 * probabilityService.calculatePxx(Xi, Cj);
            //System.out.println(Xi+"当前概率为"+ret);
        }
        // 再乘以先验概率
        ret *= probabilityService.calculatePc(Cj);
        return ret;
    }

    private String classify(String text) throws GlobalException {
        String[] terms = null;
        terms= TextUtils.ChineseSpliter(text).split(",");//中文分词处理(分词后结果已去除停用词）

        List<Department> category = dm.getCategory();//分类
        float probility = 0.0F;
        List<CategoryRes> crs = new ArrayList<CategoryRes>();//分类结果
        for (Department d : category)
        {
            String Ci = d.getName();
            probility = calcProd(terms, Ci);//计算给定的文本属性向量terms在给定的分类Ci中的分类条件概率
            //保存分类结果
            CategoryRes cr = new CategoryRes();
            cr.setCategory(Ci);//分类
            cr.setProbility(probility);;//关键字在分类的条件概率
            crs.add(cr);

            System.out.println("In process.");
            System.out.println(Ci + "：" + probility);

        }
        //对最后概率结果进行排序
        java.util.Collections.sort(crs,new Comparator()
        {
            public int compare(final Object o1,final Object o2)
            {
                final CategoryRes m1 = (CategoryRes) o1;
                final CategoryRes m2 = (CategoryRes) o2;
                final double ret = m1.getProbility() - m2.getProbility();
                if (ret < 0)
                {
                    return 1;
                }
                else
                {
                    return -1;
                }
            }
        });
        //返回概率最大的分类
        return crs.get(0).getCategory();
    }

}
