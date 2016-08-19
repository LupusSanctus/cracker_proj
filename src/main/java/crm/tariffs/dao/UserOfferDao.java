package crm.tariffs.dao;


import crm.tariffs.domain.Offer;
import crm.tariffs.domain.Usr;
import crm.tariffs.domain.UserOffer;

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
import java.util.HashMap;


@Repository
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

    public static final UpdatePreparator<UserOffer> mapUpdate = new UpdatePreparator<UserOffer>() {
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
        super(rowMapper, mapUpdate, new TableMapper(tableTitle, null, "usr_id", "offer_id"));
    }

    @Override
    protected <P extends UserOffer> P afterCreate(P domain) {
        domain.withPersisted(true);
        return domain;
    }

}
