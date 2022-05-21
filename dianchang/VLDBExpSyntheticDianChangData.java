import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * 1 设备 * 3万测点=3万序列 时间间隔：1秒 每个序列点数： 2592000（30天） 数据类型：Float。
 * <p>
 * 300 设备 * 3万测点=900万序列 时间间隔：1秒 每个序列点数： 8640（0.1天） 数据类型：Float。
 */
public class VLDBExpSyntheticDianChangData {

  public static void main(String[] args) throws IOException {
    String device = "root.DianChang.d%s";
    String out = device + ".csv";
    String out_data_type = "dianchang_data_type.csv"; // as TYPE_INFO_FILE
    PrintWriter writer_dataType = new PrintWriter(new FileOutputStream(new File(out_data_type)));
    writer_dataType.println("timeseries,dataType");
    
    int deviceNum = 300;
    int sensorNum = 30000;
    long timestamp_ms = 1577836800000L; // January 1, 2020 0:00:00 ms
    int step_ms = 1000; // 时间间隔：1秒
    int L = 8640; // 每个序列点数
    StringBuilder sensorHeader = new StringBuilder();
    sensorHeader.append("Time");
    for (int i = 1; i < sensorNum + 1; i++) {
      sensorHeader.append(",sensor" + i);
    }
    String header = sensorHeader.toString();

    for (int d = 1; d < deviceNum + 1; d++) {
      PrintWriter writer = new PrintWriter(new FileOutputStream(new File(String.format(out, d))));
      writer.println(header);
      float min = 0f;
      float max = 100f;

      for (int i = 1; i < L + 1; i++) {
        sensorHeader = new StringBuilder();
        timestamp_ms += step_ms;
        sensorHeader.append(timestamp_ms);

        for (int j = 1; j < sensorNum + 1; j++) {
          // 随机float
          float v = min + new Random().nextFloat() * (max - min);
          sensorHeader.append(",");
          sensorHeader.append(v);

          if (i == 1) {
            writer_dataType.println(String.format(device, d) + ".sensor" + j + "," + "FLOAT");
          }
        }
        writer.println(sensorHeader.toString());
//        if (i % 10000 == 0) {
//          System.out.println("Already write " + i + " rows.");
//        }
      }
      writer.close();
      System.out.println("finish write device " + d);
    }
    writer_dataType.close();
  }
}
