package com.example.Gachon_Its.config;

import com.example.Gachon_Its.service.PostService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.postdir}")
    private String postPath;

    @Value("${file.userdir}")
    private String userPath;

    private String postResourcePath = "/upload/post/**"; // view 에서 접근할 경로
    private String userResourcePath = "/upload/user/**"; // view 에서 접근할 경로
//    private String savePath = "file:///C:/Users/wlswn/spring study/gachon_image/"; // 실제 파일 경로, 이 경로를 "/upload/**"로 접근하겠다



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(postResourcePath)
                .addResourceLocations("file:///"+postPath);

        registry.addResourceHandler(userResourcePath)
                .addResourceLocations("file:///"+userPath);
    }
}
