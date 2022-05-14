import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class VLDBExpRenameDump {

  public static void main(String[] args) throws IOException {
    String sqlfile = args[0];
    BufferedReader reader = new BufferedReader(new FileReader(sqlfile));
    String line = null;
    int i = 0;
    while ((line = reader.readLine()) != null) {
      String[] items = line.split("\\s+");
      String deviceName = items[items.length - 1];
      File oldfile = new File("dump" + i + ".csv");
      File newfile = new File(deviceName + ".csv");
      if (oldfile.renameTo(newfile)) {
        System.out.println("File name changed succesfully");
      } else {
        System.out.println("Rename failed");
      }
      i++;
    }
    reader.close();
  }
}
