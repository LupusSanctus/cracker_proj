package crm.tariffs.basic;


import crm.tariffs.sql.SqlCommon;

import java.io.Serializable;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

import org.apache.commons.collections.MapUtils; //debug print

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.CrudRepository;


public abstract class JdbcCommon <T extends Persistable<ID>, ID extends Serializable> implements CrudRepository<T,ID>, InitializingBean, BeanFactoryAware {

    private final RowMapper<T> rowMapper;
    private final MapUpdate<T> mapUpdate;
    private final GetTable table;
    
    private SqlCommon sqlCommon = new SqlCommon();
    private BeanFactory beanFactory;
    
    //JdbcOperations implemented by JdbcTemplate
    private JdbcOperations jdbcOperations;

    public JdbcCommon(RowMapper<T> rowMapper, MapUpdate<T> mapUpdate, SqlCommon sqlCommon, GetTable table) {           
            this.mapUpdate = mapUpdate;
            this.rowMapper = rowMapper;
            this.sqlCommon = sqlCommon;
            this.table = table;
    }
            
    public JdbcCommon(RowMapper<T> rowMapper, MapUpdate<T> mapUpdate, String tableName, String idColumn) {
            this(rowMapper, mapUpdate, null, new GetTable(tableName, idColumn));
    }

    // for appropriate beans lifecycle
    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            jdbcOperations = beanFactory.getBean(JdbcOperations.class);
        } catch (NoSuchBeanDefinitionException e) {
            final DataSource dataSource = beanFactory.getBean(DataSource.class);
            jdbcOperations = new JdbcTemplate(dataSource);
        }
        if (sqlCommon == null) {
            try {
                sqlCommon = beanFactory.getBean(SqlCommon.class);
            } catch (NoSuchBeanDefinitionException e) {
                sqlCommon = new SqlCommon();
            }
        }
    }

    public void setSqlCommon(SqlCommon sqlCommon) {
        this.sqlCommon = sqlCommon;
    }
    
    public void setJdbcOperations(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public void setDataSource(DataSource ds) {
        jdbcOperations = new JdbcTemplate(ds);
    }

    protected GetTable getTable() {
        return table;
    }

    public static Object[] compositePrimKey(Object... idVals) {
        return idVals;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public long count() {
        return jdbcOperations.queryForObject(sqlCommon.count(table), Long.class);
    }

    @Override
    public void delete(ID id) {
        jdbcOperations.update(sqlCommon.deleteWhereId(table), idToObjectArray(id));
    }

    @Override
    public void delete(T obj) {
        jdbcOperations.update(sqlCommon.deleteWhereId(table), idToObjectArray(obj.getId()));
    }
 
    @Override
    public void delete(Iterable<? extends T> entities) {
        for (T t : entities) {
                delete(t);
        }
    }

    @Override
    public void deleteAll() {
        jdbcOperations.update(sqlCommon.deleteAll(table));
    }

    @Override
    public boolean exists(ID id) {
        return jdbcOperations.queryForObject(sqlCommon.countById(table), Integer.class, idToObjectArray(id)) > 0;
    }

    @Override
    public List<T> findAll() {
        return jdbcOperations.query(sqlCommon.selectAll(table), rowMapper);
    }

    @Override
    public T findOne(ID id) {
        final Object[] idColumns = idToObjectArray(id);
        final List<T> objOrEmpty = jdbcOperations.query(sqlCommon.selectById(table), idColumns, rowMapper);
        return objOrEmpty.isEmpty() ? null : objOrEmpty.get(0);
    }
           
    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {
        final List<ID> idsList = toList(ids);
        if (idsList.isEmpty()) {
            return Collections.emptyList();
        }
        final List<Object> res = new ArrayList<Object>();
        for (ID id : ids) {
            res.addAll(idToObjectList(id));
        }
        final Object[] idColumnValues = res.toArray();
        return jdbcOperations.query(sqlCommon.selectByIds(table, idsList.size()), rowMapper, idColumnValues);
    }

    @Override
    public <P extends T> P save(P obj) {
        if (obj.isNew()) {
           // System.out.println("NEW!!");
            return create(obj);
        } else {
           // System.out.println("NOT NEW!!");
            return update(obj);
        }
    }

    @Override
    public <P extends T> Iterable<P> save(Iterable<P> entities) {
        List<P> ret = new ArrayList<P>();
        for (P s : entities) {
            ret.add(save(s));
        }
        return ret;
    }

    protected <P extends T> P update(P obj) {
        final Map<String, Object> columns = beforeUpdate(obj, columns(obj));
        //MapUtils.debugPrint(System.out, "Columns in updt: ", columns);
        final List<Object> idValues = removeIdColumns(columns);
        final String updateQuery = sqlCommon.update(table, columns);
        for (int i = 0; i < table.getIdColumns().size(); ++i) {
                columns.put(table.getIdColumns().get(i), idValues.get(i));
        }
        final Object[] queryParams = columns.values().toArray();
        jdbcOperations.update(updateQuery, queryParams);
        return afterUpdate(obj);
    }

    protected Map<String,Object> beforeUpdate(T obj, Map<String, Object> columns) {
        return columns;
    }

    protected <P extends T> P create(P obj) {
        final Map<String, Object> columns = beforeCreate(columns(obj), obj);
        final String createQuery = sqlCommon.create(table, columns);
        
        //MapUtils.debugPrint(System.out, "Columns in CREATE: ", columns);
        
        final Object[] queryParams = columns.values().toArray();       
       
        //System.out.println("Create! " + createQuery);
        
        //update(String sql, Object... args)
        jdbcOperations.update(createQuery, queryParams);
        return afterCreate(obj);
    }

    private List<Object> removeIdColumns(Map<String, Object> columns) {
            List<Object> idColumnsValues = new ArrayList<Object>(columns.size());
            for (String idColumn : table.getIdColumns()) {
                    idColumnsValues.add(columns.remove(idColumn));
            }
            return idColumnsValues;
    }

    protected Map<String, Object> beforeCreate(Map<String, Object> columns, T obj) {
        return columns;
    }

    private LinkedHashMap<String, Object> columns(T obj) {
            return new LinkedHashMap<String, Object>(mapUpdate.mapTableData(obj));
    }

    protected <P extends T> P afterUpdate(P obj) {
            return obj;
    }             

    protected <P extends T> P afterCreate(P obj) {
            return obj;
    }
    
    // cast
    private static <T> List<T> toList(Iterable<T> it) {
        final List<T> res = new ArrayList<T>();
        for (T item : it) {
            res.add(item);
        }
        return res;
    }   

    private static <ID> Object[] idToObjectArray(ID id) {
        if (id instanceof Object[])
            return (Object[]) id;
        else
            return new Object[]{id};
    }

    private static <ID> List<Object> idToObjectList(ID id) {
        if (id instanceof Object[])
            return Arrays.asList((Object[]) id);
        else
            return Collections.<Object>singletonList(id);
    }
    
}
