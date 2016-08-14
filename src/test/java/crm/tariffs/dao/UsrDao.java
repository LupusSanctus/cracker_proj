package crm.tariffs.dao;


import crm.tariffs.obj.Usr;
import crm.tariffs.basic.JdbcCommon;
import crm.tariffs.basic.MapUpdate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;


@Repository //marks DAO beans
public class UsrDao extends JdbcCommon<Usr, Integer> {

    public static final RowMapper<Usr> usrMapper = new RowMapper<Usr>() {
        @Override
        public Usr mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Usr(
                rs.getInt("usr_id"),
                rs.getString("usr_name")
            ).withPersisted(true);
        }
    };

    public static final MapUpdate<Usr> usrMapUpdate = new MapUpdate<Usr>() {
        @Override
        public Map<String, Object> mapTableData(Usr t) {
            final LinkedHashMap<String, Object> tableData = new LinkedHashMap<String, Object>();
            tableData.put("usr_id", t.getUsrId());
            tableData.put("usr_name", t.getUsrName());
            return tableData;
        }
    };
    
    public UsrDao(String tableName) {
        super(usrMapper, usrMapUpdate, tableName, "usr_id");
    }

    @Override
    protected <S extends Usr> S afterUpdate(S entity) {
            entity.withPersisted(true);
            return entity;
    }
    
    @Override
    protected <S extends Usr> S afterCreate(S entity) {
            entity.withPersisted(true);
            return entity;
    }
    
}
