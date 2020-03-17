package cn.ganwuwang.hospital.classification;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Probability {

    private static TrainingDataManager tdm = new TrainingDataManager();
    private static final float M = 0F;
    private static double zoomFactor = 10.0f;

    /**
     * 先验概率
     * @param c 给定的分类
     * @return 给定条件下的先验概率
     */
    public static float calculatePc(String c)
    {
        float ret = 0F;
        float Nc = tdm.getTrainingFileCountOfClassification(c);
        float N = tdm.getTrainingFileCount();
        ret = Nc / N;
        return ret;
    }

    /**
     * 先验概率
     * @param x 给定的词
     * @return 给定条件下的先验概率
     */
    public static float calculatePx(String x)
    {
        float ret = 0F;
        float Nc = tdm.getCountContainKey(x);
        float N = tdm.getTrainingFileCount();
        ret = (Nc + 1) / N;
        return ret;
    }

    /**
     * 计算类条件概率
     * @param x 给定的文本属性
     * @param c 给定的分类
     * @return 给定条件下的类条件概率
     */
    public static float calculatePxc(String x, String c)
    {
        float ret = 0F;
        float Nxc = tdm.getCountContainKeyOfClassification(c, x);
        float Nc = tdm.getTrainingFileCountOfClassification(c);
        float V = tdm.getTraningClassifications().length;
        ret = (Nxc + 1) / (Nc + M + V); //为了避免出现0这样极端情况，进行加权处理
        return ret;
    }

    /**
     * 计算给定的文本属性向量X在给定的分类Cj中的类条件概率
     * <code>ClassConditionalProbability</code>连乘值
     * @param X 给定的文本属性向量
     * @param Cj 给定的类别
     * @return 分类条件概率连乘值，即<br>
     */
    public static float calcProd(String[] X, String Cj)
    {
        float ret = 1.0F;
        // 类条件概率连乘
        for (int i = 0; i <X.length; i++)
        {
            String Xi = X[i];
            //因为结果过小，因此在连乘之前放大10倍，这对最终结果并无影响，因为我们只是比较概率大小而已
            ret *= (calculatePxc(Xi, Cj) / calculatePx(Xi));
            //ret *= calculatePxc(Xi, Cj)*zoomFactor;
            //System.out.println(Xi+"当前概率为"+ret);
        }
        // 再乘以先验概率
        ret *= calculatePc(Cj);
        return ret;
    }

    public static String classify(String text)
    {
        String[] terms = null;
        terms= TextUtils.ChineseSpliter(text).split(",");//中文分词处理(分词后结果已去除停用词）

        String[] Classes = tdm.getTraningClassifications();//分类
        float probility = 0.0F;
        List<ClassifyResult> crs = new ArrayList<ClassifyResult>();//分类结果
        for (int i = 0; i <Classes.length; i++)
        {
            String Ci = Classes[i];//第i个分类
            probility = calcProd(terms, Ci);//计算给定的文本属性向量terms在给定的分类Ci中的分类条件概率
            //保存分类结果
            ClassifyResult cr = new ClassifyResult();
            cr.setClassification( Ci);//分类
            cr.setProbility(probility);;//关键字在分类的条件概率
            System.out.println("In process.");
            System.out.println(Ci + "：" + probility);
            crs.add(cr);
        }
        //对最后概率结果进行排序
        java.util.Collections.sort(crs,new Comparator()
        {
            public int compare(final Object o1,final Object o2)
            {
                final ClassifyResult m1 = (ClassifyResult) o1;
                final ClassifyResult m2 = (ClassifyResult) o2;
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
        return crs.get(0).getClassification();
    }

}
