package com.atguigu.baseservice.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket webApiConfig(){

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                //为了安全考虑，不去暴露管理员这个路径下的文件
                .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
                //错误路径也不能暴露，容易被黑客利用
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();

    }

    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                // 页面标题
                .title("网站-课程中心API文档")
                // 描述
                .description("本文档描述了课程中心微服务接口定义")
                // 版本号
                .version("1.0")
                // 创建人信息
                .contact(new Contact("Helen", "http://atguigu.com", "55317332@qq.com"))
                .build();
    }


}
