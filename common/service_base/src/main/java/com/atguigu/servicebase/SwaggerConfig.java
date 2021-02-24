package com.atguigu.servicebase;

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

/**
 * 类 描 述：TODD
 * 项目名称：guli_parent
 * 类 名 称：SwaggerConfig
 * 创建时间：2020/9/14 6:53 PM
 * 创 建 人：huanghao
 *
 * @version: V2.2
 */
@Configuration //标注配置类
@EnableSwagger2 //注解标注 Swagger2*表示应启用Swagger支持。
public class SwaggerConfig {

    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
//                .paths(Predicates.not(PathSelectors.regex("/admin/.*"))) //not 当你接口中含有 admin 和error 就不在显示
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }

    private ApiInfo webApiInfo () {
        return new ApiInfoBuilder()
                .title("Swagger-ui.html 框架查询API文档")
                .description("本文档描述了课程中心微服务接口定义")
                .version("1.0")
                .contact(new Contact("SpringBoot", "https://swagger.io/docs/", "2667275115@qq.com"))
                .build();
    }
}