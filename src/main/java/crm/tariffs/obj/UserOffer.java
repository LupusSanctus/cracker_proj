package crm.tariffs.dao;


import crm.tariffs.obj.Usr;
import crm.tariffs.obj.Offer;

import org.springframework.data.domain.Persistable;
import static crm.tariffs.basic.JdbcCommon.compositePrimKey;


public class UserOffer implements Persistable<Object[]> {
    
    private transient boolean persisted;
    
    private int usrId;
    private int offerId;  
    
    @Override
    public Object[] getId() {
        return compositePrimKey(usrId, offerId);
    }

    @Override
    public boolean isNew() {
        return !persisted;
    }
 
    public UserOffer() {} 
 
    public UserOffer(int usrId, int offerId) {
        this.usrId = usrId;
        this.offerId = offerId;
    }
    
    public Integer getUsrId() {
        return usrId;
    }
   
    public Integer getOfferId() {
        return offerId;
    }   
   
    public void setUsrId(int usrId) {
        this.usrId = usrId;
    }   
    
    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }   
 
    public UserOffer withPersisted(boolean persisted) {
        this.persisted = persisted;
        return this;
    }

}
