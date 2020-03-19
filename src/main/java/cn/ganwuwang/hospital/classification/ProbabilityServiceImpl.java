package cn.ganwuwang.hospital.classification;

import cn.ganwuwang.hospital.domain.results.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProbabilityServiceImpl {

    @Autowired
    private DataManager dm;

    private static final float M = 0F;
    private static double zoomFactor = 10.0f;

    /**
     * 先验概率
     * @param c 给定的分类
     * @return 给定条件下的先验概率
     */
    @Cacheable(cacheNames = {"pc"})
    public float calculatePc(String c) throws GlobalException {
        float ret = 0F;
        float Nc = dm.getDataCountOfCategory(c);
        float N = dm.getDataCount();
        ret = Nc / N;
        return ret;
    }

    /**
     * 先验概率
     * @param x 给定的词
     * @param c 给定的分类
     * @return 关键词与指定分类的关联程度
     */
    @Cacheable(cacheNames = {"px"})
    public float calculatePx(String x, String c) throws GlobalException {
        float ret = 0F;
        float N = dm.getCountContainKey(x);
        float Nxc = dm.getCountContainKeyOfCategory(c,x);
        ret = (Nxc + 1) / (N + 1);
        return ret;
    }

    /**
     * 计算类条件概率
     * @param x 给定的文本属性
     * @param c 给定的分类
     * @return 给定条件下的类条件概率
     */
    @Cacheable(cacheNames = {"pxc"})
    public float calculatePxc(String x, String c) throws GlobalException {
        float ret = 0F;
        float Nxc = dm.getCountContainKeyOfCategory(c, x);
        float Nc = dm.getDataCountOfCategory(c);
        float V = dm.getCategory().size();
        ret = (Nxc + 1) / (Nc + M + V); //为了避免出现0这样极端情况，进行加权处理
        return ret;
    }


}
