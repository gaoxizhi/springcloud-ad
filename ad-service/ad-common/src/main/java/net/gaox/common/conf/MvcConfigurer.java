
package net.gaox.common.conf;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author gaox·Eric
 * @Description: <p>  </p>
 * @date 2019/1/10 13:36
 */
@Configuration
public class MvcConfigurer implements WebMvcConfigurer {
    //请求映射处理适配器
    //@Autowired
    //private RequestMappingHandlerAdapter handlerAdapter;

    @PostConstruct
    public void init() {
        //		ConfigurableWebBindingInitializer initializer =
        //				(ConfigurableWebBindingInitializer) handlerAdapter.getWebBindingInitializer();
        //		if (initializer.getConversionService() != null) {
        //			GenericConversionService genericConversionService =
        //					(GenericConversionService) initializer.getConversionService();
        //			genericConversionService.addConverter(new StringToDateConverter());
        //		}
    }

    /**
     * CORS过滤器
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> swaggerAPIFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource =
                new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        FilterRegistrationBean<CorsFilter> bean =
                new FilterRegistrationBean<>(new CorsFilter(urlBasedCorsConfigurationSource));
        bean.setOrder(0);
        return bean;
    }

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.clear();
//        converters.add(jsonConverter());
//    }

    /**
     * JSON序列化 用于将controller返回的实体类转换成json串
     */
    @Bean
    public FastJsonHttpMessageConverter jsonConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
                SerializerFeature.WriteNullBooleanAsFalse
                , SerializerFeature.WriteNullNumberAsZero
                , SerializerFeature.WriteNullStringAsEmpty
                , SerializerFeature.WriteMapNullValue
                , SerializerFeature.WriteNonStringKeyAsString
                , SerializerFeature.DisableCircularReferenceDetect
//                , SerializerFeature.WriteEnumUsingName
                //设置Enum输出ToString内容
                , SerializerFeature.WriteEnumUsingToString
        );
        converter.setFastJsonConfig(config);
        return converter;
    }


//    @Autowired
//    LoginInterceptor loginInterceptor;
//
//    /**
//     * 拦截器
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
//                .excludePathPatterns("/login/**", "/account/login/**", "/error/**", "/static/**", "/hello/**");
//    }


}