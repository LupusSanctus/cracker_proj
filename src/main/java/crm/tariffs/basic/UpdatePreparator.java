package crm.tariffs.basic;


import java.util.Map;


public interface UpdatePreparator<T> {
    Map<String, Object> mapTableData(T t);
}
