package crm.tariffs.obj;


import org.springframework.data.domain.Persistable;


public class Offer implements Persistable<Integer> {
  
    private Integer offerId;
    private String offerName;
    // indicate new object state - update or save
    private transient boolean persisted;
    
    public Offer(Integer offerId, String offerName) {
        this.offerId = offerId;
        this.offerName = offerName;  
    }
    
    @Override
    public Integer getId() {
        return offerId;
    }  
       
    //returns if the object is new
    @Override
    public boolean isNew() {
        return !persisted;
    }
     
    public Integer getOfferId() {
        return offerId;
    }

    public String getOfferName() {
        return offerName;
    }
    
    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }   

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }    
    
    public Offer withPersisted(boolean persisted) {
        this.persisted = persisted;
        return this;
    }   
   
}
