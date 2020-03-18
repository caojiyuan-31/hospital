package cn.ganwuwang.hospital.classification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetHtmlData {

    private static String PATH = "http://zzk.fh21.com.cn";
    private static String REGEX_ZZ = "target=\"_blank\">\\S+</a></li>";
    private static String NEXT_PAGE = "<a href=\"/department/symptoms/\\S+\">下一页</a>";
    private static String ALL_PAGE = "<li><a href=\"/department/symptoms/\\d+.html";


    public static List<String> getAllPage(String url){
        String page = url;
        List<String> ret = new ArrayList<String>();
        List<String> list = getHtml(PATH+page,ALL_PAGE);
        for(String s : list){
            ret.add(getUrl(s));
        }

        return ret;
    }


    public static List<String> getZZForSymptoms(String url){
        String page = url;
        List<String> ret = new ArrayList<>();
        ret.addAll(getHtml(PATH+page,REGEX_ZZ));

        List<String> u = getHtml(PATH+page,NEXT_PAGE);
        if(u.size() == 0){
            page = null;
        }else {
            page = getUrl(u.get(0));
        }
        while(page != null){
            ret.addAll(getHtml(PATH+page,REGEX_ZZ));
            u = getHtml(PATH+page,NEXT_PAGE);
            if(u.size() == 0){
                page = null;
            }else {
                page = getUrl(u.get(0));
            }

        }

        return ret;
    }

    public static String getUrl(String in){
        if("".equals(in) || in == null){
            return null;
        }

        String out = in.replaceAll("\\S*<a href=\"","");
        out = out.replaceAll("\">\\S+</a>","");

        return out;
    }

    public static String getChinese(String in){
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FA5]+");
        StringBuffer sb = new StringBuffer();
        Matcher matcher = pattern.matcher(in);
        //发现匹配的字符串,存入set中
        while(matcher.find()){
            sb.append(matcher.group());
        }
        return sb.toString();
    }

    public static List<String> getHtml(String url, String regex){

        Pattern pattern = Pattern.compile(regex);
        List<String> value = new ArrayList<String>();
        try {
            //创建一个url对象来指向要采集信息的网址
            URL u = new URL(url);
            //将读取到的字节转化为字符
            InputStreamReader inStrRead = new InputStreamReader(u.openStream(),"utf-8");
            //读取InputStreamReader转化成的字符
            BufferedReader bufRead = new BufferedReader(inStrRead);
            //读到的内容不为空
            String line = bufRead.readLine();
            while ( line!= null) {
                //System.out.println(line);
                Matcher matcher = pattern.matcher(line);
                //发现匹配的字符串,存入set中
                while(matcher.find()){
                    value.add(matcher.group());
                }
                line = bufRead.readLine();
            }
            bufRead.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return value;
    }

    public static void main(String[] args) {
        //System.out.println(getUrl(getHtml(PATH+"/department/symptoms/5.html",NEXT_PAGE).get(0)));
        List<String> list = getZZForSymptoms("/department/symptoms/5.html");
        for(String o : list){
            System.out.println(o);
        }
        /*List<String> list = getAllPage("/department/symptoms/1.html");
        for(String o : list){
            System.out.println(o);
        }*/
    }

}
