package crm.tariffs.domain;


import org.springframework.data.domain.Persistable;


// integer - ID
public class Usr implements Persistable<Integer> {

    private Integer usrId;
    private String usrName;
    private transient boolean persisted;
    
    public Usr(Integer usrId, String usrName) {
        this.usrId = usrId;
        this.usrName = usrName;  
    }
    
    @Override
    public Integer getId() {
        return usrId;
    }  
       
    //returns if the object is new
    @Override
    public boolean isNew() {
        return !persisted;
    }
     
    public Integer getUsrId() {
        return usrId;
    }

    public String getUsrName() {
        return usrName;
    }
    
    public void setUsrId(int usrId) {
        this.usrId = usrId;
    }   

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }    
    
    public Usr withPersisted(boolean persisted) {
        this.persisted = persisted;
        return this;
    }    
   
}
