package crm.tariffs.basic;


import java.util.Map;


public interface MapUpdate<T> {
    Map<String, Object> mapTableData(T t);
}
