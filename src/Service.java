import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings({ "resource" })

public class Service {

  private List<Restaurant> restaurants;
  private List<User> users;
  private List<Item> items;
  private List<Order> orders;
  private User currentUser = null;

  Service() {
    this.restaurants = new ArrayList<Restaurant>();
    this.users = new ArrayList<User>();
    this.items = new ArrayList<Item>();
    this.orders = new ArrayList<Order>();

    addDummyData();
  }

  private void addDummyData() {
    restaurants.add(new Restaurant("Capriciosa", "Local de familie cu mancare buna si mancare accesibila", "Campina"));
    restaurants
        .add(new Restaurant("Bon Apetit", "Cele mai bune cocktailuri din oras la o priveliste de vis", "Scorteni"));
    restaurants
        .add(new Restaurant("Oscar", "feluri de mancare inedite pentru o experienta culinara de neuitat", "Muscel"));
    users.add(new DeliveryUser("Popescu", "Ana are mere", "073123213", "PH-44-ION"));
    users.add(new CustomerUser("Andrei Ion", "test1234", "073123413"));
    users.add(new CustomerUser("Moise ALexandru", "GTAV", "073121213"));
    items.add(new FoodItem("Pizza Capriciosa", "Pizza cu ciuperci si sunca", false));
    items.add(new FoodItem("Burger de vita", "Burger cu cartofi prajiti acompaniati de sos", false));
    items.add(new DrinkItem("Green Apple", "Un cocktail cu martini servit cu felii de mar", true));
    restaurants.get(0).addInMenu(items.get(0), 12.99f, 0.05f);
    restaurants.get(1).addInMenu(items.get(2), 2.99f, 0.07f);
    restaurants.get(2).addInMenu(items.get(1), 25.99f, 0.15f);
    currentUser = users.get(2);
    addItemToCart();
    showCartItems();

  }

  public void addRestaurant() {
    Scanner sc = new Scanner(System.in);
    System.out.print("Nume restaurant: ");
    String name = sc.nextLine();
    System.out.print("Descriere restaurant: ");
    String description = sc.nextLine();
    System.out.print("Locatie: ");
    String location = sc.nextLine();
    Restaurant restaurant = new Restaurant(name, description, location);
    restaurants.add(restaurant);
  }

  public void showAllRestaurants() {
    for (Restaurant r : restaurants) {
      System.out.println(r.toString());
    }
  }

  public void addUser() {
    int userType;
    Scanner sc = new Scanner(System.in);
    System.out.println("Alegeti tipul de user: 1.Livrator 2.Client");
    userType = sc.nextInt();
    sc.nextLine();
    System.out.println();
    System.out.print("Username: ");
    String username = sc.nextLine();
    System.out.print("Passowrd: ");
    String password = sc.nextLine();
    System.out.print("Telefon: ");
    String phoneNumber = sc.nextLine();
    User userToAdd;
    if (userType == 1) {
      System.out.print("Numar de inmatriculare: ");
      String licensePlate = sc.nextLine();
      userToAdd = new DeliveryUser(username, password, phoneNumber, licensePlate);
    } else {
      userToAdd = new CustomerUser(username, password, phoneNumber);
    }
    users.add(userToAdd);
  }

  public void showUsers() {
    for (User user : users) {
      System.out.println(user.toString());
    }
  }

  public void addItem() {
    System.out.println("Alegeti tipul de item: 1.Mancare 2.Bautura");
    Scanner sc = new Scanner(System.in);
    int itemType = sc.nextInt();
    sc.nextLine();
    System.out.println("Numele produsului: ");
    String itemName = sc.nextLine();
    System.out.println("Descriere produsului: ");
    String itemDescription = sc.nextLine();
    Item itemToAdd;
    if (itemType == 1) {
      System.out.println("Produsul este vegan? DA/NU");
      String raspuns = sc.nextLine();
      itemToAdd = new FoodItem(itemName, itemDescription, raspuns.equals("DA"));

    } else if (itemType == 2) {
      System.out.println("Produsul contine alcool? DA/NU");
      String raspuns = sc.nextLine();
      itemToAdd = new DrinkItem(itemName, itemDescription, raspuns.equals("DA"));
    } else {
      System.out.println("Comanda invalida");
      return;
    }
    items.add(itemToAdd);

  }

  public void showItems() {
    for (Item i : items) {
      System.out.println(i.toString());
    }
  }

  public void addItemToMenu() {
    Scanner sc = new Scanner(System.in);
    System.out.println("Selectati restaurantul :");
    showAllRestaurants();
    int selectedRestaurant = sc.nextInt();
    System.out.println("Selectati itemul :");
    int selectedItem = sc.nextInt();
    System.out.println("Alegeti pretul :");
    float price = sc.nextFloat();
    restaurants.get(selectedRestaurant).addInMenu(items.get(selectedItem), price, 0.0f);
  }

  public void addItemToCart() {
    Scanner sc = new Scanner(System.in);
    System.out.println("Selectati restaurantul de unde doriti sa comandati :");
    showAllRestaurants();
    int selectedRestaurant = sc.nextInt();
    Restaurant restaurant = restaurants.get(selectedRestaurant);
    System.out.println("Selectati produsul din meniu :");
    restaurant.showMenu();
    List<MenuItem> menu = restaurant.getMenu();
    int selectedItem = sc.nextInt();
    ((CustomerUser) currentUser).addItemToCart(menu.get(selectedItem));// TODO this is ugly
  }

  public void showCartItems() {
    System.out.println("Cart: ");
    for (CartItem c : ((CustomerUser) currentUser).getCart()) {
      System.out.println(c.getItem().toString() + " | " + c.getQuantity() + " buc");
    }
  }

  public void placeOrder() {
    if (!(currentUser instanceof CustomerUser))
      return;
    DeliveryUser dUser = null;
    for (User u : users) {
      if (u instanceof DeliveryUser && ((DeliveryUser) u).isDelivering()) {
        ((DeliveryUser) u).assignOrder();
        dUser = (DeliveryUser) u;
        break;
      }
    }
    if (dUser == null)
      return;
    Order orderToBePlaced = new Order(((CustomerUser) currentUser).getCart(), (CustomerUser) currentUser, dUser);
    orders.add(orderToBePlaced);
  }

  public void showOrdersForUser() {
    for (Order order : orders) {
      if (order.getCustomer() == currentUser) {
        System.out.println(order.toString());
      }
    }
  }

  public void completeCurrentDelivery() {
    if (!(currentUser instanceof DeliveryUser))
      return;
    ((DeliveryUser) currentUser).completeOrder();
  }
}
