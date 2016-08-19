package crm.tariffs.dao;


import crm.tariffs.domain.Params;
import crm.tariffs.dao.JdbcCommon;
import crm.tariffs.basic.TableMapper;
import crm.tariffs.basic.UpdatePreparator;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;


@Repository
public class ParamsDao extends JdbcCommon<Params, Object[]> {

    public static final RowMapper<Params> rowMapper = new RowMapper<Params>() {
        @Override
        public Params mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Params(
                rs.getInt("offer_id"),
                rs.getInt("attr_id"),
                rs.getString("attr_value")
            ).withPersisted(true);
        }
    };

    public static final UpdatePreparator<Params> paramsUpdatePreparator = new UpdatePreparator<Params>() {
        @Override
        public Map<String, Object> mapTableData(Params t) {
            final LinkedHashMap<String, Object> tableData = new LinkedHashMap<String, Object>();
            tableData.put("offer_id", t.getOfferId());
            tableData.put("attr_id", t.getAttrId());
            tableData.put("attr_value", t.getAttrValue());
            return tableData;
        }
    };
    
    public ParamsDao() {
        this("params");
    }

    public ParamsDao(String tableTitle) {
        super(rowMapper, paramsUpdatePreparator, new TableMapper(tableTitle, null, "offer_id", "attr_id"));
    }

    @Override
    protected <P extends Params> P afterCreate(P domain) {
        domain.withPersisted(true);
        return domain;
    }
}
  
