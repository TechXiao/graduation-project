package cn.mrxiao.graduation.config;

import cn.mrxiao.graduation.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author mrxiao
 * @date 2020/4/25 - 0:05
 **/
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/store-login","/submit-login","/produceSecurityCode","/admin","/adminLogin")
                .excludePathPatterns("/store-login/produceSecurityCode","/submit-store-login")
                .excludePathPatterns("/css/**","/webjars/**","/images/**","/js/**");//排除静态资源拦截
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/storePictures/**").addResourceLocations("file:/graduate-project/upload-data/storePictures/");
        registry.addResourceHandler("/licensePictures/**").addResourceLocations("file:/graduate-project/upload-data/licensePictures/");
        registry.addResourceHandler("/identityPictures/**").addResourceLocations("file:/graduate-project/upload-data/identityPictures/");
        registry.addResourceHandler("/wechatPictures/**").addResourceLocations("file:/graduate-project/upload-data/wechatPictures/");
        registry.addResourceHandler("/zhifubaoPictures/**").addResourceLocations("file:/graduate-project/upload-data/zhifubaoPictures/");
        registry.addResourceHandler("/foodPictures/**").addResourceLocations("file:/graduate-project/upload-data/foodPictures/");
    }
}
