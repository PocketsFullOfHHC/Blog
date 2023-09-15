// 配置需要拦截器的接口请求
package com.hhc.blogs.config;

import com.hhc.blogs.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Resource
    LoginInterceptor loginInterceptor;

//    public void addInterceptors(InterceptorRegistry registry) {
//        // 配置无需拦截的页面路径，这是拦截器的优势，两个星号表示后面的值是任意的
//        registry.addInterceptor(loginInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns(
//                        "/user/**",
//                        "/test/**",
//                        "/redis/**",
//                        // 暂时加上测试后端
//                        "/blog/**",
//                        "/**"
//                );
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/picture/**").addResourceLocations("file:///F:/GitHHC/BLOGS/src/main/resources/statics/");
    }
}
