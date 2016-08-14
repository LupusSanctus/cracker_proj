package crm.tariffs.tbasic;

import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.junit.Before;
import org.junit.Test;

import crm.tariffs.dao.UsrDao;
import crm.tariffs.obj.Usr;
import crm.tariffs.postgres.PostgresqlConfig;

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
import static crm.tariffs.postgres.PostgresqlConfig.postgresPort;


@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = PostgresqlConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class JdbcCommonTest {

    private final int dbPort;
    private String testLastname = "TestLN";
    
    public JdbcCommonTest() {
        dbPort = postgresPort;
    }
    
    @Resource
    private UsrDao usrd;

    @Resource
    private DataSource dataSource;

    private JdbcOperations jdbc;

    @Before
    public void setup() {
        jdbc = new JdbcTemplate(dataSource);
    }

    
    private Usr usr(Integer tId) {
        return new Usr(tId, testLastname);
    }
        
    @Test
    public void saveRecord() {
        Usr qwert = usr(9);
        usrd.save(qwert);
    }
    
    @Test
    public void returnUsrById() {
        boolean exists = usrd.exists(7);
        assertThat(exists).isTrue();
        Usr usr = usrd.findOne(7);
        System.out.println("USER!! = " + usr.getUsrName());
    }
    
    @Test
    public void returnRecordById() {
        jdbc.update("INSERT INTO USR VALUES (?, ?)", 10, "WDEWF");
        Usr usr = usrd.findOne(10);
        System.out.println("USER!! = " + usr.getUsrName());
    }
    
    //references on this record
//     @Test
//     public void errorWhenDeleteById() throws Exception {
//         final int testId = 7;
// //         System.out.println("TEST: delete by id");
//         usrd.delete(testId);
//     }

    // TBD other tests
    
}



