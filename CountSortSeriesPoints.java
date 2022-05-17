package org.apache.iotdb;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.iotdb.session.Session;
import org.apache.iotdb.session.SessionDataSet;
import org.apache.iotdb.tsfile.read.common.Field;
import org.apache.iotdb.tsfile.read.common.RowRecord;

public class CountSortSeriesPoints {

  public static void main(String[] args) throws Exception {
    Session session =
        new Session(
            "127.0.0.1",
            6667,
            "root",
            "root",
            true);

    session.open();

    String sql = "select count(*) from root.T000100010002.90003";
    try (SessionDataSet dataSet = session.executeQueryStatement(sql)) {
      List<String> columnNames = dataSet.getColumnNames();
      RowRecord rowRecord = dataSet.next();
      List<Field> fields = rowRecord.getFields();
      Map<String, Long> unSortedMap = new HashMap<>();
      for (int i = 0; i < columnNames.size(); i++) {
        unSortedMap.put(columnNames.get(i), fields.get(i).getLongV());
      }
      Map<String, Long> sortedMap = sortByValue(unSortedMap);
      printMap(sortedMap);
    }

    session.close();
  }

  private static Map<String, Long> sortByValue(Map<String, Long> unsortMap) {

    // 1. Convert Map to List of Map
    List<Entry<String, Long>> list =
        new LinkedList<Entry<String, Long>>(unsortMap.entrySet());

    // 2. Sort list with Collections.sort(), provide a custom Comparator
    //    Try switch the o1 o2 position for a different order
    Collections.sort(list, new Comparator<Entry<String, Long>>() {
      public int compare(Map.Entry<String, Long> o1,
          Map.Entry<String, Long> o2) {
        return (o1.getValue()).compareTo(o2.getValue());
      }
    });

    // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
    Map<String, Long> sortedMap = new LinkedHashMap<String, Long>();
    for (Map.Entry<String, Long> entry : list) {
      sortedMap.put(entry.getKey(), entry.getValue());
    }

    return sortedMap;
  }

  public static <K, V> void printMap(Map<K, V> map) {
    for (Map.Entry<K, V> entry : map.entrySet()) {
      System.out.println("Key : " + entry.getKey()
          + " Value : " + entry.getValue());
    }
  }

}
