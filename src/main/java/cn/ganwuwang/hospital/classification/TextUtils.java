package cn.ganwuwang.hospital.classification;

import org.ansj.recognition.impl.StopRecognition;
import org.ansj.splitWord.analysis.BaseAnalysis;

import java.io.*;

public class TextUtils {

    private static StopRecognition s = null;

    static {
        SetStopWord();
    }

    private static void SetStopWord(){
        s = new StopRecognition();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/stopword.txt"), "UTF-8"));
            String line = "";
            while ((line = br.readLine()) != null){  //按行读取文件流的内容
                s.insertStopWords(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String ChineseSpliter(String in){
        String out = BaseAnalysis.parse(in).recognition(s).toStringWithOutNature();
        return out;
    }

}
