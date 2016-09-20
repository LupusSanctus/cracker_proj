package crm.tariffs.dao;


import crm.tariffs.domain.Usr;
import crm.tariffs.dao.JdbcCommon;
import crm.tariffs.basic.UpdatePreparator;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

import crm.tariffs.sql.SqlCommon; 
import crm.tariffs.basic.TableMapper;


@Repository //marks DAO users
public class UsrDao extends JdbcCommon<Usr, Integer> {

    public static final RowMapper<Usr> usrMapper = new RowMapper<Usr>() {
        @Override
        public Usr mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return new Usr(
                resultSet.getInt("usr_id"),
                resultSet.getString("usr_name")
            ).withPersisted(true);
        }
    };

    public static final UpdatePreparator<Usr> usrUpdatePreparator = new UpdatePreparator<Usr>() {
        @Override
        public Map<String, Object> mapTableData(Usr t) {
            final LinkedHashMap<String, Object> tableData = new LinkedHashMap<String, Object>();
            tableData.put("usr_id", t.getUsrId());
            System.out.println("Update Preparator = " +  tableData.get("usr_id"));
            tableData.put("usr_name", t.getUsrName());
            System.out.println("Update Preparator = " +  tableData.get("usr_name"));
            return tableData;
        }
    };
    
    
    //tableName - table name in postgres: "usr"
    //(RowMapper<T> rowMapper, UpdatePreparator<T> mapUpdate, SqlCommon sqlCommon, TableMapper table)
    public UsrDao(String tableName) {
        super(usrMapper, usrUpdatePreparator, tableName, "usr_id");
        //super(usrMapper, usrUpdatePreparator, new SqlCommon("usr_name"),  new TableMapper(tableName, "usr", "usr_id"));
    }

    @Override
    protected <S extends Usr> S afterUpdate(S entity) {
            entity.withPersisted(true);
            System.out.println("Obj");
            return entity;
    }
    
    @Override
    protected <S extends Usr> S afterCreate(S entity) {
            entity.withPersisted(true);
            System.out.println("Obj");
            return entity;
    }
    
}
