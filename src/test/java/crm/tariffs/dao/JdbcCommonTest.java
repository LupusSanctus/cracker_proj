package crm.tariffs.dao;

import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.junit.Before;
import org.junit.Test;

import crm.tariffs.dao.UsrDao;
import crm.tariffs.domain.Usr;
import crm.tariffs.domain.UserOffer;
import crm.tariffs.domain.Params;
import crm.tariffs.domain.Offer;
import crm.tariffs.dao.UserOfferDao;
import crm.tariffs.sql.PostgresqlConfig;

import com.google.common.collect.Lists;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Date;
import java.util.*;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static crm.tariffs.sql.PostgresqlConfig.postgresPort;
import static crm.tariffs.dao.JdbcCommon.compositePrimKey;


@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = PostgresqlConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class JdbcCommonTest {

    private final int dbPort;
    
    // number of records in tables before changes, it`s supposed to be constant because of @Transactional
    private final int numberOfUsrRecords = 7;
    private final int numberOfOfferRecords = 6;
    
    private String testUserLastname = "TestLN";
    private String testOfferName = "TestOffer";
    
    public JdbcCommonTest() {
        dbPort = postgresPort;
    }
    
    @Resource
    private DataSource dataSource;
    private JdbcOperations jdbc;

    @Resource
    private UsrDao usrDao;
    
    @Resource
    private OfferDao offerDao;
    
    @Resource    
    private UserOfferDao userOfferDao;    
    
    @Resource    
    private ParamsDao paramsDao;
    
    @Before
    public void setup() {
        jdbc = new JdbcTemplate(dataSource);
    }

    
    private Usr usr(int testUserId) {
        return new Usr(testUserId, testUserLastname);
    }

    private Offer offer(int testOfferId) {
        return new Offer(testOfferId, testOfferName);
    }    

    //----- Usr CRUD
    @Test
    public void returnOneUsrRecordById() {
        jdbc.update("INSERT INTO USR VALUES (?, ?)", 9, "TestLN9");
        Usr usr = usrDao.findOne(9);
        assertThat(usr.getUsrId()).isEqualTo(9);
        assertThat(usr.getUsrName()).isEqualTo("TestLN9");
    }
    
    @Test
    public void returnNullWhenNotExistedUsr() throws Exception {
            Usr usr = usrDao.findOne(35);
            assertThat(usr).isNull();
    }        
    
    @Test
    public void createNewUsrRecord() {
        Usr usrRecord = usr(8);
        usrDao.save(usrRecord);
        
        System.out.println("Created user record is: " + usrRecord.getUsrId() + ", " + usrRecord.getUsrName());
        Usr createdUsrRecord = usrDao.findOne(8);
        assertThat(createdUsrRecord.getUsrId()).isEqualTo(8);
        assertThat(createdUsrRecord.getUsrName()).isEqualTo("TestLN");
    }
    
    @Test
    public void updateCreatedUsrRecord() throws Exception {
        Usr testLN = usrDao.save(usr(8));
        testLN.setUsrName("TestLNew");
        usrDao.save(testLN);
        
        System.out.println("Updated user record is: " + testLN.getUsrId() + ", " + testLN.getUsrName());
        Usr updated = usrDao.findOne(8);
        assertThat(updated.getUsrId()).isEqualTo(8);
        assertThat(updated.getUsrName()).isEqualTo("TestLNew");
    }
    
    @Test
    public void deleteUsrById() throws Exception {
        int deleteId = 10;
        jdbc.update("INSERT INTO USR VALUES (?, ?)", deleteId, "TestLN10");
        usrDao.delete(deleteId);
        assertThat(jdbc.queryForObject("SELECT COUNT(usr_id) FROM USR WHERE usr_id = ?", Integer.class, deleteId)).isZero();
    }
    
    @Test
    public void deleteUsrByObject() throws Exception {       
        int deleteId = 11;
        Usr usr = usrDao.save(usr(deleteId));
        usrDao.delete(usr);
        assertThat(jdbc.queryForObject("SELECT COUNT(usr_id) FROM USR WHERE usr_id = ?", Integer.class, deleteId)).isZero();
    }

    @Test
    public void saveMultipleUsrRecords() throws Exception {
        Usr usr11 = usr(11);
        Usr usr12 = usr(12);
        usrDao.save(Arrays.asList(usr11, usr12));
        assertThat(jdbc.queryForList("SELECT usr_id FROM USR ORDER BY usr_id", Integer.class)).containsExactly(1, 2, 3, 4, 5, 6, 7, 11, 12);
    }

    @Test
    public void deleteMultipleUsrRecords() throws Exception {
        Usr usr11 = usr(11);
        Usr usr12 = usr(12);
        usrDao.save(Arrays.asList(usr11, usr12));
        usrDao.delete(Arrays.asList(usr11, usr12));
        assertThat(jdbc.queryForObject("SELECT COUNT(usr_id) FROM USR", Integer.class)).isEqualTo(numberOfUsrRecords);
    }   
    
    // assume that table wasn`t changed (@Transactional)
    @Test
    public void returnNumberOfUsrRecords() throws Exception {
        final long count = usrDao.count();       
        assertThat(count).isEqualTo(numberOfUsrRecords);
    } 
    
    //----- Offer CRUD
    @Test
    public void returnOneOfferRecordById() {
        jdbc.update("INSERT INTO OFFER VALUES (?, ?)", 8, "TestOffer8");
        Offer offer = offerDao.findOne(8);
        assertThat(offer.getOfferId()).isEqualTo(8);
        assertThat(offer.getOfferName()).isEqualTo("TestOffer8");
    }     
    
    @Test
    public void returnNullWhenNotExistedOffer() throws Exception {
            Offer offer = offerDao.findOne(45);
            assertThat(offer).isNull();
    }    
    
    @Test
    public void createNewOfferRecord() {
        Offer offerRecord = offer(7);
        offerDao.save(offerRecord);
        
        Offer createdOfferRecord = offerDao.findOne(7);
        System.out.println("Created offer record is: " + offerRecord.getOfferId() + ", " + offerRecord.getOfferName());
        assertThat(createdOfferRecord.getOfferId()).isEqualTo(7);
        assertThat(createdOfferRecord.getOfferName()).isEqualTo("TestOffer");        
    }          
    
    @Test
    public void updateCreatedOfferRecord() throws Exception {
        Offer offerRecord = offerDao.save(offer(7));
        offerRecord.setOfferName("TestOfferNew");
        offerDao.save(offerRecord);
        
        System.out.println("Updated offer record is: " + offerRecord.getOfferId() + ", " + offerRecord.getOfferName());
        Offer updated = offerDao.findOne(7);
        assertThat(updated.getOfferId()).isEqualTo(7);
        assertThat(updated.getOfferName()).isEqualTo("TestOfferNew");
    }
    
    @Test
    public void deleteOfferById() throws Exception {
        int deleteId = 8;
        jdbc.update("INSERT INTO OFFER VALUES (?, ?)", deleteId, "TestOffer8");
        offerDao.delete(deleteId);
        assertThat(jdbc.queryForObject("SELECT COUNT(offer_id) FROM OFFER WHERE offer_id = ?", Integer.class, deleteId)).isZero();
    }    
        
    @Test
    public void deleteOfferByObject() throws Exception {       
        int deleteId = 11;
        Offer offer = offerDao.save(offer(deleteId));
        offerDao.delete(offer);
        assertThat(jdbc.queryForObject("SELECT COUNT(offer_id) FROM OFFER WHERE offer_id = ?", Integer.class, deleteId)).isZero();
    }
    
    @Test
    public void saveMultipleOfferRecords() throws Exception {
        Offer offer11 = offer(11);
        Offer offer12 = offer(12);
        offerDao.save(Arrays.asList(offer11, offer12));
        assertThat(jdbc.queryForList("SELECT offer_id FROM OFFER ORDER BY offer_id", Integer.class)).containsExactly(1, 2, 3, 4, 5, 6, 11, 12);
    }

    @Test
    public void deleteMultipleOfferRecords() throws Exception {
        Offer offer11 = offer(11);
        Offer offer12 = offer(12);
        offerDao.save(Arrays.asList(offer11, offer12));
        offerDao.delete(Arrays.asList(offer11, offer12));
        assertThat(jdbc.queryForObject("SELECT COUNT(offer_id) FROM OFFER", Integer.class)).isEqualTo(numberOfOfferRecords);
    }      
    
    // assume that table wasn`t changed (@Transactional)
    @Test
    public void returnNumberOfOfferRecords() throws Exception {
        final long count = offerDao.count();       
        assertThat(count).isEqualTo(numberOfOfferRecords);
    }
    
    //----- UserOffer CRUD
    @Test
    public void createUserOfferRecord() throws Exception {
        UserOffer record = new UserOffer(2, 2);
        userOfferDao.save(record);
        UserOffer found = userOfferDao.findOne(compositePrimKey(2, 2));
        assertThat(found.getId()).isEqualTo(record.getId());
    }
       
    @Test
    public void deleteUserOfferByCompositeKey() throws Exception {
        userOfferDao.save(new UserOffer(2, 4));
        userOfferDao.delete(compositePrimKey(2, 4));
        assertThat(userOfferDao.exists(compositePrimKey(2, 4))).isFalse();
    }
   
    @Test
    public void deleteUserOfferByObject() throws Exception {
        UserOffer record = userOfferDao.save(new UserOffer(2, 5));
        userOfferDao.delete(record);
        assertThat(userOfferDao.exists(compositePrimKey(2, 5))).isFalse();
    }
    
    //----- Params CRUD
    @Test
    public void createParamsRecord() throws Exception {
        Params record = new Params(1, 4, "TestVal");
        paramsDao.save(record);
        Params found = paramsDao.findOne(compositePrimKey(1, 4));
        assertThat(found.getId()).isEqualTo(record.getId());
    }    
    
    @Test
    public void updateParamsByCompositeKey() throws Exception {
        Params record = paramsDao.save(new Params(2, 4, "TestVal24"));
        record.setAttrValue("TestVal24Update");
        paramsDao.save(record);
        Params foundUpdated = paramsDao.findOne(compositePrimKey(2, 4));
        assertThat(foundUpdated.getId()).isEqualTo(record.getId());
    }
      
    @Test
    public void deleteParamsByCompositeKey() throws Exception {
        paramsDao.save(new Params(2, 4, "TestVal24"));
        paramsDao.delete(compositePrimKey(2, 4));
        assertThat(userOfferDao.exists(compositePrimKey(2, 4))).isFalse();
    }
   
    @Test
    public void deleteParamsByObject() throws Exception {
        Params record = paramsDao.save(new Params(1, 4, "TestVal14"));
        paramsDao.delete(record);
        assertThat(paramsDao.exists(compositePrimKey(1, 4))).isFalse();
    }          
} 
