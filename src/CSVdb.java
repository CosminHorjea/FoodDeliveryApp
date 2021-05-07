import java.io.IOException;
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
      Files.write(Paths.get(pathToCSV), "\n".getBytes(), StandardOpenOption.APPEND);
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

  public void addItemToCart(int id, int menuItemID) {
    try {
      System.out.println("ID: " + id);
      List<String> values = Files.readAllLines(Paths.get("data/cart.csv")).stream().map(line -> {
        String[] row = line.split(",");
        if (row[0].equals(Integer.toString(id))) {
          line = line.concat("," + menuItemID);
          System.out.println("Am adaugat");
        }
        return line;
      }).collect(Collectors.toList());
      System.out.println(values);
      Files.write(Paths.get("data/cart.csv"), values);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void eraseCart(int id) {
    try {
      System.out.println("ID: " + id);
      List<String> values = Files.readAllLines(Paths.get("data/cart.csv")).stream()
          .filter(line -> !line.split(",")[0].equals(Integer.toString(id))).collect(Collectors.toList());
      System.out.println(values);
      Files.write(Paths.get("data/cart.csv"), values);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
