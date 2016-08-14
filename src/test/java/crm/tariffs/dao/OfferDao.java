package crm.tariffs.dao;


import crm.tariffs.obj.Offer;
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
public class OfferDao extends JdbcCommon<Offer, Integer> {

    public static final RowMapper<Offer> offerMapper = new RowMapper<Offer>() {
        @Override
        public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Offer(
                rs.getInt("offer_id"),
                rs.getString("offer_name")
            ).withPersisted(true);
        }
    };

    public static final MapUpdate<Offer> offerMapUpdate = new MapUpdate<Offer>() {
        @Override
        public Map<String, Object> mapTableData(Offer t) {
            final LinkedHashMap<String, Object> tableData = new LinkedHashMap<String, Object>();
            tableData.put("offer_id", t.getOfferId());
            tableData.put("offer_name", t.getOfferName());
            return tableData;
        }
    };
    
    public OfferDao(String tableName) {
        super(offerMapper, offerMapUpdate, tableName, "offer_id");
    }

    @Override
    protected <S extends Offer> S afterUpdate(S entity) {
            entity.withPersisted(true);
            return entity;
    }
    
    @Override
    protected <S extends Offer> S afterCreate(S entity) {
            entity.withPersisted(true);
            return entity;
    }
    
}
