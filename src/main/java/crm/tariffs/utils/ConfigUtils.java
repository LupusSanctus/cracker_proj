package crm.tariffs.utils;


import crm.tariffs.sql.PostgresqlConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.*;


public class ConfigUtils {
    
    public AnnotationConfigApplicationContext  context;
    
    ConfigUtils(){
        context = new AnnotationConfigApplicationContext();
        context.scan("crm.tariffs.sql"); 
        context.refresh();
    }
    
}
 
