package cn.ganwuwang.hospital.controller;

import cn.ganwuwang.hospital.domain.constant.Path;
import cn.ganwuwang.hospital.domain.constant.ResultEnum;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import cn.ganwuwang.hospital.domain.results.Result;
import org.apache.commons.io.FileUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/file")
public class FileController {

    private static final String path = "src/main/resources/static/";
    private static final String img = "jpg,png,gif";

    @PostMapping("/uploadFile")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public Result upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws GlobalException {
        if (file.isEmpty()) {
            return new Result(ResultEnum.FILE_ERROR);
        }

        return new Result(Path.host+"upload/"+uploadFile(file));
    }


    private String uploadFile(MultipartFile file) throws GlobalException {
        Long name = System.currentTimeMillis();
        String fileName = file.getOriginalFilename();
        String[] list = fileName.split("\\.");
        String category = list[list.length - 1];
        String[] check = img.split(",");
        Boolean tip = false;
        for (String c : check){
            if (category.equals(c)){
                tip = true;
            }
        }
        if(!tip){
            throw new GlobalException(ResultEnum.SUCCESS);
        }

        String pathName = name + "." + category;
        String filePath = path + pathName;
        File dest = new File(filePath);
        try {
            dest.createNewFile();
            FileUtils.copyInputStreamToFile(file.getInputStream(),dest);
        } catch (IOException e) {
            e.printStackTrace();
            throw new GlobalException(ResultEnum.UPLOAD_ERROR);
        }
        return pathName;
    }

}
