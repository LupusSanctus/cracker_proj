package crm.tariffs.basic;


import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetTable {

    private final String name;
    private final List<String> idColumns;
    private final String fromClause;

    public GetTable(String name, String fromClause, String... idColumns) {
            this.name = name;
            this.idColumns = Collections.unmodifiableList(Arrays.asList(idColumns));
            if (StringUtils.hasText(fromClause)) {
                this.fromClause = fromClause;
            } else {
                this.fromClause = name;
            }
    }

    public GetTable(String name, String idColumn) {
            this(name, null, idColumn);
    }
    
    public String getName() {
        return name;
    }

    public List<String> getIdColumns() {
        return idColumns;
    }

    public String getFromClause() {
        return fromClause;
    }
}
