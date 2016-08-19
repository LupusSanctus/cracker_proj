package crm.tariffs.domain;


import org.springframework.data.domain.Persistable;
import static crm.tariffs.dao.JdbcCommon.compositePrimKey;


public class Params implements Persistable<Object[]> {

    private Integer offerId;
    private Integer attrId;
    private String attrValue;
    // indicates new object state - update or save
    private transient boolean persisted;
    
    @Override
    public Object[] getId() {
        return compositePrimKey(offerId, attrId);
    }  
       
    @Override
    public boolean isNew() {
        return !persisted;
    }   
    
    public Params(Integer offerId, Integer attrId, String attrValue) {
        this.offerId = offerId;
        this.attrId = attrId;  
        this.attrValue = attrValue;
    }
    
    public Params() {}
    public Integer getOfferId() {
        return offerId;
    }
    
    public Integer getAttrId() {
        return attrId;
    }
    
    public String getAttrValue() {
        return attrValue;
    }
    
    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }  
    
    public void setAttrId(int attrId) {
        this.attrId = attrId;
    }
    
    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }    
    
    public Params withPersisted(boolean persisted) {
        this.persisted = persisted;
        return this;
    }   
  
}
