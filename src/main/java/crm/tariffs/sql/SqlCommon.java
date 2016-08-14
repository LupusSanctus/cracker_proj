package crm.tariffs.sql; 

import crm.tariffs.basic.GetTable; 
import java.util.*;


public class SqlCommon {

    public static final String WHERE = " WHERE ";
    public static final String AND = " AND ";
    public static final String OR = " OR ";
    public static final String SELECT = "SELECT ";
    public static final String FROM = "FROM ";
    public static final String DELETE = "DELETE ";
    public static final String COMMA = ", ";
    public static final String PARAM = " = ?";
    private String clauses;
    
    public SqlCommon(String clauses) {
        this.clauses = clauses;
    }

    public SqlCommon() {
        this("*");
    }

    public String count(GetTable table) {
        return SELECT + "COUNT(*) " + FROM + table.getFromClause();
    }

    public String deleteWhereId(GetTable table) {
        return DELETE + FROM + table.getName() + whereId(table);
    }

    private String whereId(GetTable table) {
        final StringBuilder whereClause = new StringBuilder(WHERE);
        for (Iterator<String> idColIterator = table.getIdColumns().iterator(); idColIterator.hasNext(); ) {
            whereClause.append(idColIterator.next()).append(PARAM);
            if (idColIterator.hasNext()) {
                whereClause.append(AND);
            }
        }
        return whereClause.toString();
    }

    public String selectAll(GetTable table) {
        return SELECT + clauses + " " + FROM + table.getFromClause();
    }


    public String selectById(GetTable table) {
        return selectAll(table) + whereId(table);
    }

    public String selectByIds(GetTable table, int ids) {
        switch (ids) {
            case 0:
                return selectAll(table);
            case 1:
                return selectById(table);
            default:
                return selectAll(table);// + whereByIdsClause(table, ids);
        }
    }

    public String update(GetTable table, Map<String, Object> columns) {
        final StringBuilder updateQuery = new StringBuilder("UPDATE " + table.getName() + " SET ");
        for(Iterator<Map.Entry<String,Object>> iterator = columns.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<String, Object> column = iterator.next();
            updateQuery.append(column.getKey()).append(" = ?");
            if (iterator.hasNext()) {
                updateQuery.append(COMMA);
            }
        }
        updateQuery.append(whereId(table));
        return updateQuery.toString();
    }
    
    public String create(GetTable table, Map<String, Object> columns) {
            final StringBuilder formQuery = new StringBuilder("INSERT INTO " + table.getName() + " (");
            mergeColTitles(formQuery, columns.keySet());
            formQuery.append(")").append(" VALUES (");
            //System.out.println("COLUMNS SIZE = " + columns.size());
            formQuery.append(repeat("?", COMMA, columns.size()));
            return formQuery.append(")").toString();
    }

    private void mergeColTitles(StringBuilder formQuery, Set<String> columnNames) {
        for(Iterator<String> it = columnNames.iterator(); it.hasNext();) {
            final String column = it.next();
            formQuery.append(column);
            if (it.hasNext()) {
                formQuery.append(COMMA);
            }
        }
    }

    public String deleteAll(GetTable table) {
            return DELETE + FROM + table.getName();
    }

    public String countById(GetTable table) {
            return count(table) + whereId(table);
    }
    
    private static String repeat(String str, String delim, int count) {
        StringBuilder string = new StringBuilder((str.length() + delim.length()) * count);
            for(int i = 1; i < count; i++) {              
                string.append(str).append(delim);
            }
        return string.append(str).toString();
    }    
        
}
