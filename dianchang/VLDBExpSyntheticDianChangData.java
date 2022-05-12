import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * 1 设备 * 3万测点=3万序列 时间间隔：1秒 每个序列点数： 2592000（30天） 数据类型：Float
 */
public class VLDBExpSyntheticDianChangData {

  public static void main(String[] args) throws IOException {
    String out = "DianChang_d1.csv";
    PrintWriter writer = new PrintWriter(new FileOutputStream(new File(out)));
    int n = 30000; // 1 设备 * 3万测点=3万序列
    StringBuilder sb = new StringBuilder();
    sb.append("Time");
    for (int i = 1; i < n + 1; i++) {
      sb.append(",sensor" + i);
    }
    writer.println(sb.toString()); // HEADER
    long timestamp_ms = 1577836800000L; // January 1, 2020 0:00:00 ms
    int step_ms = 1000; // 时间间隔：1秒
    int L = 2592000; // 每个序列点数： 2592000（30天）
    float min = 0f;
    float max = 100f;
    for (int i = 1; i < L + 1; i++) {
      sb = new StringBuilder();
      timestamp_ms += step_ms;
      sb.append(timestamp_ms);
      for (int j = 0; j < n; j++) {
        // 随机float
        float v = min + new Random().nextFloat() * (max - min);
        sb.append(",");
        sb.append(v);
      }
      writer.println(sb.toString());
      if (i % 10000 == 0) {
        System.out.println("Already write " + i + " rows.");
      }
    }
    writer.close();
  }
}
