package cn.ganwuwang.hospital.utils;

import com.alibaba.druid.util.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CheckUtils {

    public static boolean isNotEmptyBatch(String... strParams) {
        return !isEmptyBatch(strParams);
    }

    public static boolean isEmptyBatch(String... strParams) {
        for (String str : strParams) {
            if (StringUtils.isEmpty(str)) {
                return true;
            }
        }

        return false;
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
