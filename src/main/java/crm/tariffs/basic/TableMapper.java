package crm.tariffs.basic;


import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TableMapper {

    private final String name;   
    // table id
    private final List<String> idColumns;
    private final String fromClause;

    public TableMapper(String name, String fromClause, String... idColumns) {
            this.name = name;
            this.idColumns = Collections.unmodifiableList(Arrays.asList(idColumns));
            if (StringUtils.hasText(fromClause)) {
                this.fromClause = fromClause;
            } else {
                this.fromClause = name;
            }
            System.out.println("FROM = " + this.fromClause);
    }

    public TableMapper(String name, String idColumn) {
        this(name, null, idColumn);
    }
    
    public String getName() {
        return name;
    }

    public List<String> getIdColumns() {
        //usr_id
        for(int i = 0; i < idColumns.size(); i++) {
            System.out.println("IdColumns: " + idColumns.get(i));
        }
        return idColumns;
    }

    public String getFromClause() {
        return fromClause;
    }
}
