import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * wc -l -c * >tmp 查看所有文件行数、字节数
 */
public class VLDBExpStat {

  public static void main(String[] args) throws IOException {
    String file = "D:\\1\\tmp";
    BufferedReader reader = new BufferedReader(new FileReader(file));
    PrintWriter writer = new PrintWriter(new FileOutputStream(new File("yanchang_stat.csv")));
    String line;
    writer.println("deviceName,pointNum(AllSensorsAligned),fileSize(Bytes)");
    while ((line = reader.readLine()) != null) {
      String[] items = line.split("\\s+");
//      System.out.println(items[1]);
      int pointNum = Integer.parseInt(items[1]) - 1; // -1 for skip header
      int fileSize = Integer.parseInt(items[2]);
      String device = items[3].substring(0, items[3].length() - 4);
      writer.println(device + "," + pointNum + "," + fileSize);
    }
    reader.close();
    writer.close();
  }
}
