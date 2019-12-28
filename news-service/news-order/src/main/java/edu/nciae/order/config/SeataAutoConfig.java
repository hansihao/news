package edu.nciae.order.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import io.seata.spring.annotation.GlobalTransactionScanner;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * seata 配置
 */
@Configuration
public class SeataAutoConfig {
    // mybatis配置
    private String mapperLocations = "edu.nciae.order.mapper./**.xml";
    private String typeAliasesPackage = "edu.nciae.order.domain";
    private boolean mapUnderscoreToCamelCase = true;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }


    @Primary//@Primary标识必须配置在代码数据源上，否则本地事务失效
    @Bean("dataSourceProxy")
    public DataSourceProxy dataSourceProxy(DataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }


    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSourceProxy dataSourceProxy) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSourceProxy);
        factoryBean.setTypeAliasesPackage(typeAliasesPackage);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(mapperLocations));
        factoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
        factoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);
        return factoryBean.getObject();
    }

    @Bean
    public GlobalTransactionScanner scanner(){
        GlobalTransactionScanner scanner = new GlobalTransactionScanner("order", "my_test_tx_group");
        return scanner;
    }
}
