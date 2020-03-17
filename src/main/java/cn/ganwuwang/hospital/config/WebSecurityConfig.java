package cn.ganwuwang.hospital.config;

import cn.ganwuwang.hospital.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserServiceImpl userService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService);
    }

    /**
     * 1）HttpSecurity支持cors。
     * 2）默认会启用CRSF，此处因为没有使用thymeleaf模板（会自动注入_csrf参数），
     * 要先禁用csrf，否则登录时需要_csrf参数，而导致登录失败。
     * 3）antMatchers：匹配 "/" 路径，不需要权限即可访问，匹配 "/user" 及其以下所有路径，
     *  都需要 "USER" 权限
     * 4）配置登录地址和退出地址
     */
    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/test/exception").hasRole("USER")
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/hello")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login");

        //以下这句就可以控制单个用户只能创建一个session，也就只能在服务器登录一次
        http.sessionManagement().maximumSessions(1).expiredUrl("/login");
    }*/

}
