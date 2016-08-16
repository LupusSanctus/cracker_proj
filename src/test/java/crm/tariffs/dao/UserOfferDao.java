package crm.tariffs.dao;


import crm.tariffs.obj.Offer;
import crm.tariffs.obj.Usr;
import crm.tariffs.basic.JdbcCommon;
import crm.tariffs.basic.GetTable;
import crm.tariffs.basic.MapUpdate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.HashMap;


@Repository //marks DAO beans
public class UserOfferDao extends JdbcCommon<UserOffer, Object[]> {

    public static final RowMapper<UserOffer> rowMapper = new RowMapper<UserOffer>() {
        @Override
        public UserOffer mapRow(ResultSet rs, int rowNum) throws SQLException {
            final UserOffer usrOffer = new UserOffer();
            usrOffer.setUsrId(rs.getInt("usr_id"));
            usrOffer.setOfferId(rs.getInt("offer_id"));
            return usrOffer.withPersisted(true);
        }
    };

    public static final MapUpdate<UserOffer> mapUpdate = new MapUpdate<UserOffer>() {
        @Override
        public Map<String, Object> mapTableData(UserOffer usrOffer) {
            final HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("usr_id", usrOffer.getUsrId());
            map.put("offer_id", usrOffer.getOfferId());
            return map;
        }
    };
    
    public UserOfferDao() {
        this("user_offer");
    }

    public UserOfferDao(String tableTitle) {
        super(rowMapper, mapUpdate, new GetTable(tableTitle, null, "usr_id", "offer_id")
        );
    }

    @Override
    protected <P extends UserOffer> P afterCreate(P obj) {
        obj.withPersisted(true);
        return obj;
    }

}
