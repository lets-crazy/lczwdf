package com.lcz.wdf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web相关配置
 *
 * @author li.chengzhen
 * @version 1.0
 * @date 2021/3/8 11:34
 **/
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        //默认地址（可以是页面或后台请求接口）
        registry.addViewController("/").setViewName("forward:/index.html");
        //设置过滤优先级最高
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

}
