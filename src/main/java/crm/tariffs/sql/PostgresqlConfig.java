package crm.tariffs.sql;


import crm.tariffs.dao.UsrDao;
import crm.tariffs.dao.OfferDao;
import crm.tariffs.dao.UserOfferDao;
import crm.tariffs.dao.ParamsDao;

import crm.tariffs.sql.SqlCommon;

import org.postgresql.jdbc2.optional.PoolingDataSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@EnableTransactionManagement
@Configuration
@ComponentScan(basePackages = "crm.tariffs.sql")
public class PostgresqlConfig {

    public static final int postgresPort = 5432;
   
    @Bean
    public UsrDao usrDao() {
        return new UsrDao("usr");
    }
    
    @Bean
    public OfferDao offerDao() {
        return new OfferDao("offer");
    }
    
    @Bean
    public UserOfferDao userOfferDao() {
        return new UserOfferDao("user_offer");
    }
      
    @Bean
    public ParamsDao paramsDao() {
        return new ParamsDao("params");
    }
    
    @Bean
    public SqlCommon sqlCoommon() {
        return new SqlCommon();
    }

    @Bean
    public DataSource dataSource() {
        PoolingDataSource dataSrc = new PoolingDataSource();
        dataSrc.setUser("radagast");
        dataSrc.setPassword("");
        dataSrc.setDatabaseName("bd");
        return dataSrc;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
