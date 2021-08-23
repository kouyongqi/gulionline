package com.atguigu.edu.config;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    /*mybatis-plus 3.3.2 版本及以下只支持 PaginationInterceptor分页 mybatis-plus 3.4.0 版本及以上推荐使用 MybatisPlusInterceptor分页 */
    /*分页组件*/
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /*逻辑删除组件*/
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

}
