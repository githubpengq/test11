package com.config.component;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.beans.PropertyVetoException;


@Configuration
@EnableJpaRepositories(basePackages = {"com.cotton.dao"})
@MapperScan(basePackages = {"com.cotton.dao"}, sqlSessionFactoryRef = "sqlSessionFactory")
@EnableTransactionManagement
public class JdbcConfig {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private Environment env;

    @Bean(destroyMethod = "close")
    public ComboPooledDataSource getDataSource() {
        logger.warn("使用jdbc方式创建数据库连接");
        ComboPooledDataSource cd = new ComboPooledDataSource();
        try {
            cd.setDriverClass(this.env.getProperty("jdbc.driverClass"));
            cd.setJdbcUrl(this.env.getProperty("jdbc.url"));
            cd.setUser(this.env.getProperty("jdbc.user"));
            cd.setPassword(this.env.getProperty("jdbc.password"));
            cd.setInitialPoolSize(NumberUtils.toInt(this.env.getProperty("jdbc.initialPoolSize")));
            cd.setMinPoolSize(NumberUtils.toInt(this.env.getProperty("jdbc.minPoolSize")));
            cd.setMaxPoolSize(NumberUtils.toInt(this.env.getProperty("jdbc.maxPoolSize")));
            cd.setMaxIdleTime(NumberUtils.toInt(this.env.getProperty("jdbc.maxIdleTime")));
            cd.setIdleConnectionTestPeriod(NumberUtils.toInt(this.env.getProperty("jdbc.idleConnectionTestPeriod")));
            cd.setCheckoutTimeout(NumberUtils.toInt(this.env.getProperty("jdbc.checkoutTimeout")));
        } catch (PropertyVetoException e) {
            logger.error("创建数据源失败", e);
        }
        return cd;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(getDataSource());
        em.setPackagesToScan(new String[]{"com.cotton.model"});
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.MYSQL);
        vendorAdapter.setDatabasePlatform("com.config.mysql.MySQL5DialectUTF8");
        vendorAdapter.setGenerateDdl(BooleanUtils.toBoolean(this.env.getProperty("jpa.generate.ddl")));
        vendorAdapter.setShowSql(BooleanUtils.toBoolean(this.env.getProperty("jpa.show.sql")));
        em.setJpaVendorAdapter(vendorAdapter);
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        return sessionFactory.getObject();
    }
}
