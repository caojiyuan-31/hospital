package cn.ganwuwang.hospital.config;

import cn.ganwuwang.hospital.domain.constant.Path;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //映射图片保存地址
        registry.addResourceHandler("/upload/**")
                .addResourceLocations(Path.path + "\\src\\main\\resources\\static\\");
    }
}
