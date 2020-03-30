package cn.ganwuwang.hospital.config;

import cn.ganwuwang.hospital.domain.constant.ResultEnum;
import cn.ganwuwang.hospital.domain.results.Result;
import cn.ganwuwang.hospital.domain.results.GlobalException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(value = GlobalException.class)
    @ResponseBody
    public Result globalErrorHandler(GlobalException e){

        return new Result(e);

    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public Result accessErrorHandler(AccessDeniedException e){

        return new Result(ResultEnum.ACCESS_ERROR);

    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    @ResponseBody
    public Result loadErrorHandler(UsernameNotFoundException e){

        Result result = new Result(ResultEnum.LOAD_ERROR);
        result.setMsg(e.getMessage());
        return result;

    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result errorHandler(Exception e){

        e.printStackTrace();
        return new Result(ResultEnum.ERROR);

    }

}
