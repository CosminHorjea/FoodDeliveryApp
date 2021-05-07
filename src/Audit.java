import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Audit
 */
public class Audit {
  private static Audit instance = null;
  private final String auditPath = "data/audit.csv";
  private FileWriter fw;

  public static Audit getInstance() {
    if (instance == null) {
      try {
        instance = new Audit();

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return instance;

  }

  private Audit() throws IOException {
    fw = new FileWriter(auditPath, true);
  }

  public void writeLog(String message) {
    try {
      fw.write(message + "," + (new Date()) + "\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void closeAudit() throws IOException {
    fw.flush();
    fw.close();
    return;
  }
}