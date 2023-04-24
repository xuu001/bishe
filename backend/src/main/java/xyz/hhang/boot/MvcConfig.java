package xyz.hhang.boot;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
//@ComponentScan(basePackages = "xyz.hhang.boot.controller")
//@EnableWebMvc
public class MvcConfig  implements WebMvcConfigurer {

//    @Autowired LoginInterceptor loginInterceptor;

//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("/WEB-INF/");
//        resolver.setSuffix(".jsp");
//        registry.viewResolver(resolver);
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/css/");
        registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/");
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry ) {
////        registry.addInterceptor(new LoginInterceptor());
//        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
//        .excludePathPatterns("/user/**").excludePathPatterns("/css/**")
//        .excludePathPatterns("/js/**").excludePathPatterns("/images/**");
//    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedOrigins("http://localhost:63342")
//                .allowedMethods("POST", "PUT", "GET", "DELETE").allowCredentials(true);
//    }


}
