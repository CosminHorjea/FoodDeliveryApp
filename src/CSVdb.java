import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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
    AtomicInteger added = new AtomicInteger(0);

    try {
      System.out.println("ID: " + id);
      List<String> values = Files.readAllLines(Paths.get("data/cart.csv")).stream().map(line -> {
        String[] row = line.split(",");
        if (row[0].equals(Integer.toString(id))) {
          line = line.concat("," + menuItemID);
          added.set(1);
        }
        return line;
      }).collect(Collectors.toList());
      if (added.intValue() == 0) {
        values.add(id + "," + menuItemID);
      }
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

  public void markOrderAsCompleted(int orderID) {
    try {
      List<String> values = Files.readAllLines(Paths.get("data/orders.csv")).stream().map(line -> {
        String[] row = line.split(",");
        if (row[1].equals(Integer.toString(orderID)) && row[row.length - 1].equals("false")) {
          row[row.length - 1] = "true";
          return Stream.of(row).collect(Collectors.joining(","));
        }
        return line;
      }).collect(Collectors.toList());
      System.out.println(values);
      Files.write(Paths.get("data/orders.csv"), values);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
