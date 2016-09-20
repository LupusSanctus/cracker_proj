package crm.tariffs.utils;


import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

import crm.tariffs.utils.ConfigUtils;
import crm.tariffs.dao.OfferDao;
import crm.tariffs.dao.UserOfferDao;


public class DbConnectionUtils {

    private static ConfigUtils configUtils = new ConfigUtils();
    
    
    public static Object loadBean(String dataSourceName) {
        return configUtils.context.getBean(dataSourceName);
    }
    
    public static JdbcOperations getJdbcOperations() {       
        DataSource dataSource = (DataSource) loadBean("dataSource");
        JdbcOperations jdbcOperations = new JdbcTemplate(dataSource);    
        return jdbcOperations;
    }
    
    public static OfferDao getOfferDaoBean() {
        OfferDao offerDao = (OfferDao) loadBean("offerDao");
        return offerDao;
    }
    
    public static UserOfferDao userOfferDao() {
        UserOfferDao userOfferDao = (UserOfferDao) loadBean("userOfferDao");
        return userOfferDao;
    }
}
