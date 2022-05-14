import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class VLDBExpSelectSqlfile {
// dump0.csv来自count timeseries root.T000100010002 group by level=2
  public static void main(String[] args) throws IOException {
    String file = "D:\\1\\apache-iotdb-0.11.2-bin\\apache-iotdb-0.11.2\\tools\\dump0.csv";
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String line = null;
    String csvSplitBy = ",";
    while ((line = reader.readLine()) != null) {
//      System.out.println(line);
      String[] items = line.split(csvSplitBy);
//      System.out.println(items[1]);
      System.out.println("select * from " + items[1]);
    }
    reader.close();
  }
}
