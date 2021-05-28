import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.Collectors;

@SuppressWarnings({ "resource" })

public class Service {
  private static Service instance = null;
  private Connection connection;
  private List<Restaurant> restaurants;
  private List<User> users;
  private List<Item> items;
  private List<Order> orders;
  private List<MenuItem> menuItems;
  private User currentUser = null;
  private Audit audit;
  private CSVdb db;
  private Database dbDerby;

  private Service() {
    this.restaurants = new ArrayList<Restaurant>();
    this.users = new ArrayList<User>();
    this.items = new ArrayList<Item>();
    this.orders = new ArrayList<Order>();
    this.menuItems = new ArrayList<MenuItem>();
    db = CSVdb.getInstance();
    audit = Audit.getInstance();
    try{
      dbDerby = new Database();
//      loadDataFromDB();
      loadDataFromCSV();
//      dbDerby.createTables();
//      dbDerby.insertRestaurant(new Restaurant(2,"capri","campina","cel mai tare"));
//      dbDerby.deleteRestaurant(new Restaurant(0,"capri","campina","cel mai tare"));
//      restaurants = dbDerby.readRestaurants();
//      System.out.println("Din db");
//      for(Restaurant r : restaurants){
//        System.out.println(r);
//      }
//      dbDerby.close();
    }catch(Exception e){
      e.printStackTrace();
    }
    boolean notFound = true;

  }

  public static Service getInstance() {
    if (instance == null)
      instance = new Service();
    return instance;
  }

  private void loadDataFromCSV() {
    // restaurants.add(new Restaurant("Capriciosa", "Local de familie cu mancare
    // buna si mancare accesibila", "Campina"));
    // restaurants
    // .add(new Restaurant("Bon Apetit", "Cele mai bune cocktailuri din oras la o
    // priveliste de vis", "Scorteni"));
    // restaurants
    // .add(new Restaurant("Oscar", "feluri de mancare inedite pentru o experienta
    // culinara de neuitat", "Muscel"));
    restaurants = (db.readDataFromCSV("data/restaurants.csv")).stream().map(line -> new Restaurant(line.split(",")))
        .collect(Collectors.toList());
    // users.add(new DeliveryUser("Popescu", "Ana are mere", "073123213",
    // "PH-44-ION"));
    // users.add(new CustomerUser("Andrei_Ion", "test1234", "073123413"));
    // users.add(new CustomerUser("Moise_Alexandru", "GTAV", "073121213"));
    // users.add(new CustomerUser("test", "test", "073121213"));
    // loading users
    users = db.readDataFromCSV("data/users.csv").stream().map(line -> User.createUser(line.split(",")))
        .collect(Collectors.toList());
    // items.add(new FoodItem("Pizza Capriciosa", "Pizza cu ciuperci si sunca",
    // false));
    // items.add(new FoodItem("Burger de vita", "Burger cu cartofi prajiti
    // acompaniati de sos", false));
    // items.add(new DrinkItem("Green Apple", "Un cocktail cu martini servit cu
    // felii de mar", true));
    // loading items
    items = db.readDataFromCSV("data/items.csv").stream().map(line -> Item.createItem(line.split(",")))
        .collect(Collectors.toList());
    // loading the menu
    db.readDataFromCSV("data/menu.csv").stream().forEach(line -> {
      String[] values = line.split(",");
      MenuItem mi = new MenuItem(items.get(Integer.parseInt(values[1])), Float.parseFloat(values[2]),
          Float.parseFloat(values[3]));
      menuItems.add(mi);
      restaurants.get(Integer.parseInt(values[0])).addInMenu(mi);
    });

    // db.readDataFromCSV("data/menu.csv").stream().forEach(line -> {
    // String[] values = line.split(",");
    // restaurants.get(Integer.parseInt(values[0])).addInMenu(items.get(Integer.parseInt(values[1])),
    // Float.parseFloat(values[2]), Float.parseFloat(values[3]));
    // });

    // restaurants.get(0).addInMenu(items.get(0), 12.99f, 0.05f);
    // restaurants.get(1).addInMenu(items.get(2), 2.99f, 0.07f);
    // restaurants.get(2).addInMenu(items.get(1), 25.99f, 0.15f);
    // String[] data = { "Scorpion", "Shaowrma cu de toate", "Mogador" };
    // db.writeDataToCSV(data, "data/restaurants.csv");

    // Creation of each users cart
    db.readDataFromCSV("data/cart.csv").stream().forEach(line -> {
      String[] values = line.split(",");
      CustomerUser userToMakeCart = (CustomerUser) users.get(Integer.parseInt(values[0]));
      for (int i = 1; i < values.length; i++) {
        userToMakeCart.addItemToCart(menuItems.get(Integer.parseInt(values[i])));
      }
    });

    // currentUser = users.get(2);
    // ((CustomerUser)
    // currentUser).addItemToCart(restaurants.get(0).getMenu().get(0));
    // ((CustomerUser)
    // currentUser).addItemToCart(restaurants.get().getMenu().get(0));
    // showCartItems();

    // reading all orders
    db.readDataFromCSV("data/orders.csv").stream().forEach(line -> {
      String[] values = line.split(",");
      TreeSet<CartItem> auxCart = new TreeSet<>(new ComparatorMenuItem());
      CustomerUser customer = (CustomerUser) users.get(Integer.parseInt(values[0]));
      DeliveryUser delivery = (DeliveryUser) users.get(Integer.parseInt(values[1]));
      for (int i = 2; i < values.length - 1; i++) {
        MenuItem item = menuItems.get(Integer.parseInt(values[i]));
        for (CartItem c : auxCart) {
          if (c.getItem().getMenuItemID() == item.getMenuItemID()) {
            c.increaseQuantity();
            continue;
          }
        }
        auxCart.add(new CartItem(item, 1));
      }
      Order newOrder = new Order(auxCart, customer, delivery);
      if (values[values.length - 1].equals("true")) {
        newOrder.completeOrder();
      }
      orders.add(newOrder);
    });

  }
  private void loadDataFromDB(){
    try{
//      this.users = dbDerby.readUser();
//      this.restaurants = dbDerby.readRestaurants();
//      this.items = dbDerby.readItems();
//      this.menuItems = dbDerby.readMenuItems();
    }catch (Exception e){
      e.printStackTrace();
    }
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
    db.writeDataToCSV(new String[] { name, description, location }, "data/restaurants.csv");
    audit.writeLog("Restaurantul " + name + " a fost adaugat");
  }

  public void showAllRestaurants() {
    int i = 1;
    for (Restaurant r : restaurants) {
      System.out.println(i++ + ": " + r.toString());
    }
    audit.writeLog("Au fost afisate toate restaurantele");
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
      db.writeDataToCSV(new String[] { username, password, phoneNumber, licensePlate }, "data/users.csv");
    } else {
      userToAdd = new CustomerUser(username, password, phoneNumber);
//      db.writeDataToCSV(new String[] { username, password, phoneNumber }, "data/users.csv");
      try{
        dbDerby.insertUser(userToAdd);
      }catch (Exception e){
        e.printStackTrace();
      }
    }
    users.add(userToAdd);
    audit.writeLog("Utilizatorul " + username + " a fost creat");
  }

  public void showUsers() {
    for (User user : users) {
      System.out.println(user.toString());
    }
    audit.writeLog("Au fost afisati toti utilizatorii");
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
      String response = sc.nextLine();
      itemToAdd = new FoodItem(itemName, itemDescription, response.equals("DA"));
      db.writeDataToCSV(new String[] { itemName, itemDescription, Boolean.toString(response.equals("DA")) },
          "data/items.csv");

    } else if (itemType == 2) {
      System.out.println("Produsul contine alcool? DA/NU");
      String response = sc.nextLine();
      itemToAdd = new DrinkItem(itemName, itemDescription, response.equals("DA"));
      db.writeDataToCSV(new String[] { itemName, itemDescription, Boolean.toString(response.equals("DA")), "bautura" },
          "data/items.csv");
    } else {
      System.out.println("Comanda invalida");
      return;
    }
    items.add(itemToAdd);
    audit.writeLog("A fost adaugat produsul " + itemName);

  }

  public void showItems() {
    for (Item i : items) {
      System.out.println(i.toString());
    }
    audit.writeLog("Au fost afisate toate produsele");
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
    db.writeDataToCSV(new String[] { Integer.toString(selectedRestaurant), Integer.toString(selectedItem),
        Float.toString(price), "0" }, "data/menu.csv");
    restaurants.get(selectedRestaurant).addInMenu(items.get(selectedItem), price, 0.0f);
  }

  public void showMenu() {
    Scanner sc = new Scanner(System.in);
    System.out.println("Alegeti restaurantul pentru care doriti sa vizualizari meniul:");
    int i = 1;
    for (Restaurant r : restaurants) {
      System.out.println(i++ + " " + r.toString());
    }
    int restaurantSelection = sc.nextInt();
    Restaurant restaurant = restaurants.get(restaurantSelection - 1);
    restaurant.showMenu();
    audit.writeLog("A fost afisat meniul de la restaurantul " + restaurantSelection);
  }

  public void addItemToCart() {
    if (!(currentUser instanceof CustomerUser))
      return;
    Scanner sc = new Scanner(System.in);
    System.out.println("Selectati restaurantul de unde doriti sa comandati :");
    showAllRestaurants();
    int selectedRestaurant = sc.nextInt();
    Restaurant restaurant = restaurants.get(selectedRestaurant - 1);
    System.out.println("Selectati produsul din meniu :");
    restaurant.showMenu();
    List<MenuItem> menu = restaurant.getMenu();
    int selectedItem = sc.nextInt();
    MenuItem mi = menu.get(selectedItem - 1);
    if (mi.getItem() instanceof DrinkItem) {
      if (((DrinkItem) mi.getItem()).isContainsAlcohool()) {
        System.out.println("Aveti peste 18 ani? DA/NU");
        sc.nextLine();
        String response = sc.nextLine();
        if (response.equals("NU"))
          return;
      }
    }
    ((CustomerUser) currentUser).addItemToCart(mi);
    audit.writeLog(currentUser.getUsername() + " a adaugat " + mi.getItem().getItemName() + " in cos");
    db.addItemToCart(currentUser.getId(), mi.getMenuItemID());

  }

  public void showCartItems() {
    System.out.println("Cart: ");
    for (CartItem c : ((CustomerUser) currentUser).getCart()) {
      System.out.println(c.getItem().toString() + " | " + c.getQuantity() + " buc");
    }
    audit.writeLog("Utilizatorul " + currentUser.getUsername() + " si-a afisat cosul de cumparaturi");
  }

  public void placeOrder() {
    if (!(currentUser instanceof CustomerUser))
      return;
    if (((CustomerUser) currentUser).getCart().size() == 0) {
      System.out.println("Cosul este gol");
      return;
    }
    DeliveryUser dUser = null;
    for (User u : users) {
      if (u instanceof DeliveryUser && !((DeliveryUser) u).isDelivering()) {
        ((DeliveryUser) u).assignOrder();
        dUser = (DeliveryUser) u;
        break;
      }
    }
    if (dUser == null) {
      System.out.print("Nu sunt livratori disponibil in aces moment, te rugam sa plasezi comanda mai tarziu!");
      return;
    }

    Order orderToBePlaced = new Order(((CustomerUser) currentUser).getCart(), (CustomerUser) currentUser, dUser);
    orders.add(orderToBePlaced);
    audit.writeLog("Comanda cu id-ul " + orderToBePlaced.getOrderID() + " a fost plasata");

    List<String> data = new ArrayList<String>();
    data.add(Integer.toString(currentUser.getId()));
    data.add(Integer.toString(dUser.getId()));
    for (CartItem c : ((CustomerUser) currentUser).getCart()) {
      data.add(Integer.toString(c.getItem().getMenuItemID()));
    }
    data.add("false");
    String[] dataArr = new String[data.size()];
    data.toArray(dataArr);
    db.writeDataToCSV(dataArr, "data/orders.csv");
    ((CustomerUser) currentUser).emptyCart();
    db.eraseCart(currentUser.getId());

  }

  public void showOrdersForUser() {
    for (Order order : orders) {
      if (order.getCustomer().getId() == currentUser.getId()) {
        System.out.println(order.toString());
      }
    }
    audit.writeLog("Utilizatorul " + currentUser.getUsername() + " a afisat comenzile");
  }

  public void completeCurrentDelivery() {
    if (!(currentUser instanceof DeliveryUser))
      return;
    ((DeliveryUser) currentUser).completeOrder();
    for (Order o : orders) {
      if (o.getDelivery().getId() == currentUser.getId()) {
        db.markOrderAsCompleted(o.getOrderID());
      }
    }
    audit.writeLog("Livratorul " + currentUser.getUsername() + " a completat o comanda");
  }

  public void loginUser() {
    Scanner sc = new Scanner(System.in);
    System.out.println("Introduceti Username-ul");
    String username = sc.nextLine();
    System.out.println("Introduceti parola");
    String password = sc.nextLine();
    for (User u : users) {
      System.out.println("Username:"+u.getUsername());
      System.out.println("Password:" + u.getPassword());
      System.out.println("------");
      if (u.getUsername().equalsIgnoreCase(username)) {
        if (u.verifyPassword(password)) {
          System.out.println("Sunteti logat ca " + username);
          currentUser = u;
          audit.writeLog("Utilizatorul " + username + " s-a logat");
          return;
        }
      }
    }
    System.out.println("Nu am gasit userul sau parola a fost introdusa incorect");
  }

  public void logoutUser() {
    audit.writeLog("Utilizatorul " + currentUser.getUsername() + " s-a delogat");
    this.currentUser = null;
  }

  public boolean isLoggedIn() {
    return currentUser != null;
  }

  public boolean isDeliveryLoggedIn() {
    return currentUser instanceof DeliveryUser;
  }

  public boolean isCustomerLoggedIn() {
    return currentUser instanceof CustomerUser;
  }

  public void showOrederToDeliver() {
    for (Order o : orders) {
      if (o.getDelivery().getId() == currentUser.getId() && !o.isDelivered()) {
        System.out.println(o.toString());
        return;
      }
    }
  }

  public void closeAudit() {
    try {
      audit.closeAudit();
      dbDerby.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void showMenuItems() {
    for (MenuItem m : menuItems) {
      System.out.println(m);
    }
  }
}
