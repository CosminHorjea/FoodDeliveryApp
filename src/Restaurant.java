import java.util.ArrayList;
import java.util.List;

public class Restaurant {
  private int restaurantID;
  private String name;
  private String description;
  private String location;
  List<MenuItem> menu;
  static int countRestaurants = 0;

  public Restaurant() {
  }

  public Restaurant(String name, String description, String location) {
    this.restaurantID = countRestaurants++;
    this.location = location;
    this.description = description;
    this.name = name;
    menu = new ArrayList<MenuItem>();
  }

  public Restaurant(String[] values) {
    this.restaurantID = countRestaurants++;
    this.name = values[0];
    this.description = values[1];
    this.location = values[2];
    menu = new ArrayList<MenuItem>();
  }

  public void addInMenu(Item item, float price, float discount) {
    MenuItem itemToAddInMenu = new MenuItem(item, price, discount);
    menu.add(itemToAddInMenu);
  }

  public void showMenu() {
    int i = 1;
    for (MenuItem mi : menu) {
      System.out.println(i++ + ": " + mi.getItem().toString() + "Pret: " + mi.getPrice());
    }
  }

  public List<MenuItem> getMenu() {
    return this.menu;
  }

  @Override
  public String toString() {
    return "Restaurant " + this.name + "\nDescriere: " + description + " \nAdresa: " + this.location + "\n";
  }
}
