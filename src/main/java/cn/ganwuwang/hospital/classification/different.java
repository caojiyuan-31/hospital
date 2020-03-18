package cn.ganwuwang.hospital.classification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class different {

    @Autowired
    Probability probability;

    private String[] traningFileClassifications;//测试语料分类集合
    private File traningTextDir;//测试语料存放目录
    private static String defaultPath = "D:\\test\\test";

    public different()
    {
        traningTextDir = new File(defaultPath);
        if (!traningTextDir.isDirectory())
        {
            throw new IllegalArgumentException("训练语料库搜索失败！ [" +defaultPath + "]");
        }
        this.traningFileClassifications = traningTextDir.list();
    }


    /**
     * 返回训练文本类别，这个类别就是目录名
     * @return 训练文本类别
     */
    public String[] getTraningClassifications()
    {
        return this.traningFileClassifications;
    }
    /**
     * 根据训练文本类别返回这个类别下的所有训练文本路径（full path）
     * @param classification 给定的分类
     * @return 给定分类下所有文件的路径（full path）
     */
    public String[] getFilesPath(String classification)
    {
        File classDir = new File(traningTextDir.getPath() +File.separator +classification);
        String[] ret = classDir.list();
        for (int i = 0; i < ret.length; i++)
        {
            ret[i] = traningTextDir.getPath() +File.separator +classification +File.separator +ret[i];
        }
        return ret;
    }
    /**
     * 返回给定路径的文本文件内容
     * @param filePath 给定的文本文件路径
     * @return 文本内容
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String getText(String filePath) throws FileNotFoundException, IOException
    {
        InputStreamReader isReader =new InputStreamReader(new FileInputStream(filePath),"GBK");
        BufferedReader reader = new BufferedReader(isReader);
        String aline;
        StringBuilder sb = new StringBuilder();
        while ((aline = reader.readLine()) != null)
        {
            sb.append(aline);
        }
        isReader.close();
        reader.close();
        return sb.toString();
    }
    /**
     * 返回训练文本集中所有的文本数目
     * @return 训练文本集中所有的文本数目
     */
    public int getTrainingFileCount()
    {
        int ret = 0;
        for (int i = 0; i < traningFileClassifications.length; i++)
        {
            ret +=getTrainingFileCountOfClassification(traningFileClassifications[i]);
        }
        return ret;
    }
    /**
     * 返回训练文本集中在给定分类下的训练文本数目
     * @param classification 给定的分类
     * @return 训练文本集中在给定分类下的训练文本数目
     */
    public int getTrainingFileCountOfClassification(String classification)
    {
        File classDir = new File(traningTextDir.getPath() +File.separator +classification);
        return classDir.list().length;
    }

    public int differentOfClassification(String classification) throws Exception {
        int ret = 0;
        String[] path = getFilesPath(classification);
        for(String p : path){
            String t = getText(p);
            System.out.println(t);
            if(!classification.equals(probability.classify(t))){
                ret++;
            };
        }
        return ret;
    }


    public void differentCount() throws Exception {
        int ret = 0;
        for(String p : traningFileClassifications){
            int c = differentOfClassification(p);
            ret += c;
            System.out.println(p+" 分类对比，该分类测试用例总数为："+getTrainingFileCountOfClassification(p)+"错误总数为："+c+"错误率为"+(double)c/getTrainingFileCountOfClassification(p));
        }
        System.out.println("分类对比结束，测试用例总数为："+getTrainingFileCount()+"错误总数为："+ret+"错误率为"+(double)ret/getTrainingFileCount());
    }
}



