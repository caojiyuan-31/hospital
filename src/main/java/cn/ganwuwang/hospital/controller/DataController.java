package cn.ganwuwang.hospital.controller;

import cn.ganwuwang.hospital.classification.*;
import cn.ganwuwang.hospital.domain.constant.ResultEnum;
import cn.ganwuwang.hospital.domain.pojo.Department;
import cn.ganwuwang.hospital.domain.pojo.LoadUser;
import cn.ganwuwang.hospital.domain.query.Page;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.domain.results.Result;
import cn.ganwuwang.hospital.service.DepartmentServiceImpl;
import cn.ganwuwang.hospital.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
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
    public Result forecastCategory(@RequestBody String text) throws GlobalException {

        Object o = CheckUtils.getAuthentication().getPrincipal();
        LoadUser u = null;
        if(o instanceof LoadUser){
            u = (LoadUser)o;
        }else {
            throw new GlobalException(ResultEnum.LOAD_ERROR);
        }

        if(CheckUtils.isEmptyBatch(text)){
            return new Result(ResultEnum.DATA_ERROR);
        }
        /*String name = classify(text);
        Department d = new Department();
        d.setName(name);
        Department re = departmentService.queryPageList(new Page(1, 20), null, d).get(0);*/

        List<CategoryRes> re = classify(text);
        Double c = re.get(0).getProbility();
        re.get(0).setProbility(5.0D);

        for(int i = 1 ; i < re.size() ; i++){
            re.get(i).setProbility(setProbability(re.get(i).getProbility(), c));
        }

        return new Result(re);

    }

    private Double setProbability(Double in, Double c){
        Double re = 0.1D;
        Integer n = 4;
        Double d = in / c;


        while (d < 0.1){
            d = d * 10;
            n = n - 1;
            if(n == 0){
                return re;
            }
        }

        DecimalFormat df=new DecimalFormat("0.0");
        re = n + Double.valueOf(df.format(d));

        return re;
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
            //因为结果过小，因此在连乘之前放大100倍
            ret *= probabilityService.calculatePxc(Xi, Cj)*100 * probabilityService.calculatePx(Xi, Cj);

        }
        // 再乘以先验概率
        ret *= probabilityService.calculatePc(Cj);
        return ret;
    }

    private List<CategoryRes> classify(String text) throws GlobalException {
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
        //return crs.get(0).getCategory();
        return crs;
    }

}
