package crm.tariffs.tbasic;


import  crm.tariffs.dao.UsrDao;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

public abstract class TestConfig {


    @Bean
    public abstract DataSource dataSource();

    /*
        @Bean
        public FooRepository fooRepository() {
            // configure and return a class having @Transactional methods
            return new JdbcFooRepository(dataSource());
        }
                            |
                            V
        <bean id="fooRepository" class="com.foo.JdbcFooRepository">
            <constructor-arg ref="dataSource"/>
        </bean>
    */
        
        @Bean
        public UsrDao usrDao() {
            return new UsrDao("users");
        }

        /*
        @Bean
        public OfferDao offerDao() {
            return new OfferDao("offers");
        }
        */
        
        @Bean
        public PlatformTransactionManager transactionManager() {
                return new DataSourceTransactionManager(dataSource());
        }

}

