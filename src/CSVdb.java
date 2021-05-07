import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVdb {
  private static CSVdb instance;

  public static CSVdb getInstance() {
    if (instance == null)
      instance = new CSVdb();
    return instance;
  }

  public void writeDataToCSV(String[] data, String pathToCSV) {
    try {
      Files.write(Paths.get(pathToCSV), (Stream.of(data).collect(Collectors.joining(",")).getBytes()),
          StandardOpenOption.APPEND);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public List<String> readDataFromCSV(String pathToCSV) {
    List<String> lines;
    try {
      lines = Files.readAllLines(Paths.get(pathToCSV));
      return lines;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;

  }
}
