package crm.tariffs.utils;


import java.util.*;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.*;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;

import crm.tariffs.domain.Usr;
import crm.tariffs.domain.UserOffer;
import crm.tariffs.dao.UserOfferDao;
import crm.tariffs.domain.Offer;
import static crm.tariffs.dao.JdbcCommon.compositePrimKey;


public class DbInfoUtils {
    //usrId = password
    //admin(1234)
    private final String adminName = "admin";
    private final Integer adminPswd = 123;
    
    public static Usr findUser(JdbcOperations jdbcOperations, String userName, Integer usrId) throws SQLException {

        if(userName.equals("admin") && usrId.intValue() == 123) {
            
        }
        
        String sqlQuery = "SELECT * FROM usr WHERE usr_name ='" + userName + "' AND usr_id = '" + usrId + "'";

        System.out.println("User name is " + userName);
        System.out.println("Password (the same as id) is " + usrId);
        System.out.println("Sql query: " + sqlQuery); 

        List<Map<String, Object>> list = jdbcOperations.queryForList(sqlQuery);
        if(list != null && !list.isEmpty()) {
            Usr user = new Usr();
            for (Map<String, Object> map : list) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    //key is column name (usr_id, usr_name)
                    if(entry.getKey().equals("usr_name")) {
                        user.setUsrName((String) entry.getValue());
                    } else {
                        user.setUsrId((Integer) entry.getValue());
                    }
                }
            }
            return user;
        }
        
        return null;
    }
    
    //DbInfoUtils.queryMoreInfoOffer(jdbcOperations, code)
    public static List<Map<String, Object>> queryMoreInfoOffer(JdbcOperations jdbcOperations, Integer offerId) {
        String sqlQuery = "select offer.offer_id, offer.offer_name, offer_attributes.attr_name, offer_attributes.description, params.attr_value from offer, params, offer_attributes where offer.offer_id = '" + offerId + "'and params.offer_id = offer.offer_id and params.attr_id = offer_attributes.attr_id;";
        
        List<Map<String, Object>> list = jdbcOperations.queryForList(sqlQuery);
//         if(list != null && !list.isEmpty()) {
//             Usr user = new Usr();
//             for (Map<String, Object> map : list) {
//                 for (Map.Entry<String, Object> entry : map.entrySet()) {
//                     //key is column name (usr_id, usr_name)
//                     if(entry.getKey().equals("usr_name")) {
//                         user.setUsrName((String) entry.getValue());
//                     } else {
//                         user.setUsrId((Integer) entry.getValue());
//                     }
//                 }
//             }
//         }
        
        return list;  
    
    }
        
    public static List<Offer> queryOffer(JdbcOperations jdbcOperations, Integer usrId) {
//         select offer.offer_id, offer.offer_name from offer, user_offer where user_offer.usr_id = 2 and
//         user_offer.offer_id = offer.offer_id
        
        List<Offer> list = new ArrayList<Offer>();
        String sqlQuery = "select user_offer.offer_id from user_offer where user_offer.usr_id = '" + usrId + "'";
        int emptyEx = 0;
        int offerId = 0;
        
        try {        
            offerId = jdbcOperations.queryForInt(sqlQuery);            
        } catch (EmptyResultDataAccessException e) {
            emptyEx = 1;
        }
        
        if(emptyEx == 1) {
            Offer offer = new Offer(0, "None");
            list.add(offer);
        } else {
            Offer offer = DbConnectionUtils.getOfferDaoBean().findOne(offerId);
            list.add(offer);
        }
        return list;     
        
    } 
    
    public static void createOffer(JdbcOperations jdbcOperations, Integer usrId, Integer code) {
        UserOffer newRecord = new UserOffer(usrId, code);
        DbConnectionUtils.userOfferDao().save(newRecord);
    }
    
    public static List<Offer> queryAllOffers(JdbcOperations jdbcOperations) {
        List<Offer> list = new ArrayList<Offer>();
        //List<Offer> list = offerDao.findAll();
        list = DbConnectionUtils.getOfferDaoBean().findAll();
        return list;
    }

    //DbInfoUtils.deleteOffer(user.getUsrId(), code);
    public static void deleteOffer(Integer usrId, Integer offerId) throws SQLException {
        DbConnectionUtils.userOfferDao().delete(compositePrimKey(usrId, offerId));
    }
    
}
