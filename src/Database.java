import com.sun.source.tree.Tree;

import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Database implements AutoCloseable{
    private final Connection connection;
    public Database() throws SQLException{
        this.connection = DriverManager.getConnection("jdbc:derby:foodDeliveryDB;create=true");

    }
    @Override
    public void close() throws Exception {
        connection.close();
    }

    public void createTables() throws SQLException {
        boolean notFound=true;
        String[] tables = {"cart","drinkItem","foodItem","menu_items","orders","restaurant","customer","delivery"};
        int[] didCreate = {0,0,0,0,0,0,0,0};
        ResultSet results = connection.getMetaData().getTables(null, null, null, new String[]{"TABLE"});
//        System.out.println("TABELE");
        while(results.next()){
            for(int i =0 ;i<tables.length;i++){
                if(tables[i].equalsIgnoreCase(results.getString("TABLE_NAME"))){
//                    System.out.println(tables[i] + " Este creat");
                    didCreate[i]=1;
                }
            }
        }

        for(int i =0;i<tables.length;i++){
            if(didCreate[i] == 0) {
                if (tables[i].equalsIgnoreCase("restaurant")) {
                    connection.createStatement().execute("CREATE TABLE restaurant (id char(36) primary key, name varchar(50), location varchar(50),description varchar(200))");
                } else if (tables[i].equalsIgnoreCase("customer")) {
                    connection.createStatement().execute("CREATE TABLE customer (id char(36)  primary key, username varchar(50), password varchar(50),phoneNumber varchar(20))");
                } else if (tables[i].equalsIgnoreCase("delivery")) {
                    connection.createStatement().execute("CREATE TABLE delivery (id char(36)  primary key, username varchar(50), password varchar(50),phoneNumber varchar(20), licensePlate varchar(20))");
                } else if (tables[i].equalsIgnoreCase("foodItem")) {
                    connection.createStatement().execute("CREATE TABLE foodItem (id char(36)  primary key, itemName varchar(50), itemDescription varchar(50),isVegan varchar(5))");
                } else if (tables[i].equalsIgnoreCase("drinkItem")) {
                    connection.createStatement().execute("CREATE TABLE drinkItem (id char(36)  primary key, itemName varchar(50), itemDescription varchar(50), containsAlcohol varchar(5))");
                } else if (tables[i].equalsIgnoreCase("orders")) {
                    connection.createStatement().execute("CREATE TABLE orders (id char(36) primary key, customer_id char(36), delivery_id char(36), delivered varchar(5))");
                }else if(tables[i].equalsIgnoreCase("orders_items")){
                    connection.createStatement().execute("CREATE TABLE orders_items(id char(36) primary key, order_id char(36), menu_item_id char(36))");
                }else if (tables[i].equalsIgnoreCase("menu_items")) {
                    connection.createStatement().execute("CREATE TABLE menu_items (id char(36) primary key, restaurant_id char(36) , item_id char(36) , price varchar(50) , discount varchar(50))");
                }else if (tables[i].equalsIgnoreCase("cart")){
                    connection.createStatement().execute("CREATE TABLE cart (id char(36) primary key, customer_id char(36),menu_item_id char(36))");
                }

            }
        }
    }

    //Restaurants
    public Restaurant insertRestaurant(Restaurant restaurant) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("INSERT INTO restaurant(id, name, location, description) VALUES (?,?,?,?)");
        statement.setString(1,Integer.toString(restaurant.getRestaurantID()));
        statement.setString(2,restaurant.getName());
        statement.setString(3,restaurant.getLocation());
        statement.setString(4,restaurant.getDescription());
        if(statement.executeUpdate() == 1){
            return restaurant;
        }
        return null;
    }
    public List<Restaurant> readRestaurants() throws SQLException{
        List<Restaurant> restaurants = new ArrayList<>();
        ResultSet results = connection.createStatement().executeQuery("SELECT * FROM restaurant");
        while (results.next()){
            restaurants.add(new Restaurant(Integer.parseInt(results.getString(1).strip()),results.getString(2),results.getString(3),results.getString(4)));
        }
        return restaurants;
    }
    public boolean updateRestaurant(Restaurant restaurant) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("UPDATE restaurant SET name = ?, location = ?, description = ? WHERE id = ?");
        statement.setString(1 , restaurant.getName());
        statement.setString(2 , restaurant.getLocation());
        statement.setString(3 , restaurant.getDescription());
        statement.setString(4 , Integer.toString(restaurant.getRestaurantID()));
        return statement.executeUpdate()==1;
    }
    public boolean deleteRestaurant(Restaurant restaurant)throws  SQLException{
        PreparedStatement statement = connection.prepareStatement("DELETE FROM restaurant WHERE id = ?");
        statement.setString(1,Integer.toString(restaurant.getRestaurantID()));
        return statement.executeUpdate() == 1;
    }

    //Users
    public User insertUser(User user) throws SQLException{
        PreparedStatement statement;
        if(user instanceof CustomerUser)
            statement = connection.prepareStatement("INSERT INTO customer(id,username,password,phoneNumber) VALUES (?,?,?,?)");
        else
            statement = connection.prepareStatement("INSERT INTO delivery(id,username,password,phoneNumber,licensePlate) VALUES (?,?,?,?,?)");

        statement.setString(1,Integer.toString(user.getId()));
        statement.setString(2,user.getUsername());
        statement.setString(3,user.getPassword());
        statement.setString(4,user.getPhoneNumber());
        if(user instanceof DeliveryUser){
            statement.setString(5,((DeliveryUser)user).getLicensePlate());
        }
        if(statement.executeUpdate() == 1){
            return user;
        }
        return null;
    }
    public List<User> readUser() throws SQLException{
        List<User> users = new ArrayList<>();
        ResultSet resultsCustomer = connection.createStatement().executeQuery("SELECT * FROM customer");
        ResultSet resultDelivery = connection.createStatement().executeQuery("SELECT * FROM delivery");

        while (resultsCustomer.next()){
            users.add(new CustomerUser(Integer.parseInt(resultsCustomer.getString(1).strip()),
                    resultsCustomer.getString(2),
                    resultsCustomer.getString(3),
                    resultsCustomer.getString(4)));
        }
        while (resultDelivery.next()){
            users.add(new DeliveryUser(Integer.parseInt(resultDelivery.getString(1).strip()),resultDelivery.getString(2),
                    resultDelivery.getString(3),resultDelivery.getString(4), resultDelivery.getString(5)));

        }
        return users;
    }
    public boolean updateUser(CustomerUser user) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("UPDATE customer SET username = ?, password = ?, phoneNumber = ? WHERE id = ?");
        statement.setString(1 , user.getUsername());
        statement.setString(2 , user.getPassword());
        statement.setString(3 , user.getPhoneNumber());
        statement.setString(4 , Integer.toString(user.getId()));
        return statement.executeUpdate()==1;
    }
    public boolean updateUser(DeliveryUser user) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("UPDATE delivery SET username = ?, password = ?, phoneNumber = ? WHERE id = ?");
        statement.setString(1 , user.getUsername());
        statement.setString(2 , user.getPassword());
        statement.setString(3 , user.getPhoneNumber());
        statement.setString(4 , Integer.toString(user.getId()));
        return statement.executeUpdate()==1;
    }
    public boolean deleteUser(User user)throws  SQLException{
        PreparedStatement statement = connection.prepareStatement("DELETE FROM customer WHERE id = ?");
        statement.setString(1,Integer.toString(user.getId()));
        PreparedStatement statement2 = connection.prepareStatement("DELETE FROM delivery WHERE id = ?");
        statement2.setString(1,Integer.toString(user.getId()));
        return statement.executeUpdate() == 1 || statement2.executeUpdate() == 1;
    }

    //Items
    public Item insertItem(Item item)throws SQLException{
        PreparedStatement statement;
        if(item instanceof FoodItem){
            statement= connection.prepareStatement("INSERT INTO foodItem(id,itemName,itemDescription,isVegan)");
            statement.setString(4,Boolean.toString(((FoodItem)item).isVegan()));
        }else{
            statement= connection.prepareStatement("INSERT INTO drinkItem(id,itemName,itemDescription,containsAlcohol)");
            statement.setString(4,Boolean.toString(((DrinkItem)item).isContainsAlcohool()));
        }
        statement.setString(1,Integer.toString(item.getItemID()));
        statement.setString(2,item.getItemName());
        statement.setString(3,item.getItemDescription());

        if(statement.executeUpdate() == 1){
            return item;
        }
        return null;

    }
    public List<Item> readItems() throws SQLException{
        List<Item> items = new ArrayList<>();
        ResultSet resultsFood = connection.createStatement().executeQuery("SELECT * FROM foodItem");
        ResultSet resultDrink = connection.createStatement().executeQuery("SELECT * FROM drinkItem");
        while (resultDrink.next()){
            items.add(new DrinkItem(Integer.parseInt(resultDrink.getString(1).strip()),resultDrink.getString(2),
                    resultDrink.getString(3),Boolean.parseBoolean(resultDrink.getString(4).strip())));
        }
        while (resultsFood.next()){
            items.add(new FoodItem(Integer.parseInt(resultsFood.getString(1).strip()),resultsFood.getString(2),
                    resultsFood.getString(3),Boolean.parseBoolean(resultsFood.getString(4).strip())));
        }
        return items;

    }
    public boolean updateItem(FoodItem item) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("UPDATE foodItem SET itemName = ?, itemDescription = ?, isVegan = ? WHERE id = ?");
        statement.setString(1,item.getItemName());
        statement.setString(2,item.getItemDescription());
        statement.setString(3,Boolean.toString(item.isVegan()));
        return statement.executeUpdate()==1;
    }
    public boolean updateItem(DrinkItem item) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("UPDATE drinkItem SET itemName = ?, itemDescription = ?, containsAlcohol = ? WHERE id = ?");
        statement.setString(1,item.getItemName());
        statement.setString(2,item.getItemDescription());
        statement.setString(3,Boolean.toString(item.isContainsAlcohool()));
        return statement.executeUpdate()==1;
    }
    public boolean deleteItem(Item item) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("DELETE FROM foodItem WHERE id = ?");
        PreparedStatement statement2 = connection.prepareStatement("DELETE FROM foodItem WHERE id = ?");
        statement.setString(1,Integer.toString(item.getItemID()));
        statement2.setString(1,Integer.toString(item.getItemID()));
        return  statement.executeUpdate() ==1 || statement2.executeUpdate() == 1;
    }

    //Orders
    public Order insertOrder(Order order) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("INSERT INTO orders(id,customer_id,delivery_id,delivered) VALUES(?,?,?,?)");
        statement.setString(1,Integer.toString(order.getOrderID()));
        statement.setString(2,Integer.toString(order.getCustomer().getId()));
        statement.setString(3,Integer.toString(order.getDelivery().getId()));
        statement.setString(4,Boolean.toString(order.isDelivered()));

        PreparedStatement statement2 = connection.prepareStatement("INSERT INTO orders_items(id,order_id,menu_id) VALUES(?,?,?)");
        for(CartItem i : order.getItems()){
            statement2.setString(1,UUID.randomUUID().toString());
            statement2.setString(2,Integer.toString(order.getOrderID()));
            statement2.setString(3,Integer.toString(i.getItem().getMenuItemID()));
        }
        if(statement.executeUpdate()==1){
            return order;
        }
        return null;
    }
    public List<Order> readOrders() throws SQLException{
        List<Order> result = new ArrayList<>();
        List<MenuItem> menuItems = readMenuItems();
        List<User> users= readUser();
        ResultSet orderResult = connection.createStatement().executeQuery("SELECT * FROM orders");
        while(orderResult.next()){
            String orderID = orderResult.getString(1);
            ResultSet itemsResult = connection.createStatement().executeQuery("SELECT * FROM order_items WHERE order_id = "+orderID);
            HashMap<String,Integer> freqItems = new HashMap<String, Integer>();
            while (itemsResult.next()){
                String itemID = itemsResult.getString(3);
                if(freqItems.containsKey(itemID)){
                    freqItems.put(itemID,freqItems.get(itemID)+1);
                }else{
                    freqItems.put(itemID,1);
                }
            }
            TreeSet<CartItem> auxCart = new TreeSet<>(new ComparatorMenuItem());
            for(Map.Entry<String,Integer> entry : freqItems.entrySet()){
                MenuItem menuItem = menuItems.stream()
                        .filter(e -> e.getMenuItemID() == Integer.parseInt(entry.getKey()))
                        .collect(Collectors.toList()).get(0);
                int quantity = entry.getValue();
                auxCart.add(new CartItem(menuItem,quantity));
            }
            int customerID = Integer.parseInt(orderResult.getString(2).strip());
            CustomerUser customer = (CustomerUser) users.stream()
                    .filter(e -> e.getId() == customerID)
                    .collect(Collectors.toList()).get(0);
            int deliveryID = Integer.parseInt(orderResult.getString(3).strip());
            DeliveryUser delivery = (DeliveryUser) users.stream()
                    .filter(e -> e.getId() == customerID)
                    .collect(Collectors.toList()).get(0);
            result.add(new Order(Integer.parseInt(orderResult.getString(1)),
                    auxCart,customer,delivery,
                    Boolean.parseBoolean(orderResult.getString(4).strip())));
        }

        return result;
    }
    public boolean updateOrder(Order order) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("UPDATE orders SET delivered = ? WHERE id = ?");
        statement.setString(1,Boolean.toString(order.isDelivered()));
        statement.setString(2,Integer.toString(order.getOrderID()));
        return statement.executeUpdate()==1;
    }
    public boolean deleteOrder(Order order) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("DELETE FROM orders WHERE id = ?");
        statement.setString(1,Integer.toString(order.getOrderID()));
        return statement.executeUpdate() == 1;
    }


    //Menu Items
    public MenuItem insertMenu(MenuItem menuItem) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("INSERT INTO menu_items(id,restaurant_id,item_id,price,discount) VALUES (?,?,?,?,?)");
        statement.setString(1,Integer.toString(menuItem.getMenuItemID()));
        statement.setString(2,Integer.toString(1));
        statement.setString(3,Integer.toString(menuItem.getItem().getItemID()));
        statement.setString(4,Float.toString(menuItem.getPrice()));
        statement.setString(5,Float.toString(menuItem.getDiscount()));
        if(statement.executeUpdate() == 1)
            return menuItem;
        return null;
    }
    public boolean insertMenu(Restaurant restaurant) throws SQLException{
        for(MenuItem menuItem : restaurant.getMenu()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO menu_items(id,restaurant_id,item_id,price,discount) VALUES (?,?,?,?,?)");
            statement.setString(1,Integer.toString(menuItem.getMenuItemID()));
            statement.setString(2,Integer.toString(restaurant.getRestaurantID()));
            statement.setString(3,Integer.toString(menuItem.getItem().getItemID()));
            statement.setString(4,Float.toString(menuItem.getPrice()));
            statement.setString(5,Float.toString(menuItem.getDiscount()));
            if(statement.executeUpdate() != 1)
                return false;
        }
        return true;
    }
    public List<MenuItem> readMenuItems() throws SQLException{
        List<MenuItem> menuItems = new ArrayList<>();
        List<Item> items = readItems();
        ResultSet menuResults = connection.createStatement().executeQuery("SELECT * FROM menu_items");
        while (menuResults.next()){
            int menuItemID = Integer.parseInt(menuResults.getString(1).strip());
            int itemID = Integer.parseInt(menuResults.getString(2).strip());
            float price = Float.parseFloat(menuResults.getString(3).strip());
            float discount = Float.parseFloat(menuResults.getString(4).strip());
            menuItems.add(new MenuItem(menuItemID,
                    items.stream().filter(e -> e.getItemID() == itemID).collect(Collectors.toList()).get(0),price,discount));
        }
        return menuItems;
    }
    public boolean updateMenuItem(MenuItem menuItem) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("UPDATE menu_items SET price = ?, discount = ? WHERE id = ?");
        statement.setString(1,Float.toString(menuItem.getPrice()));
        statement.setString(2,Float.toString(menuItem.getDiscount()));
        statement.setString(3,Integer.toString(menuItem.getMenuItemID()));
        return statement.executeUpdate() == 1;
    }
    public boolean deleteMenuItem(MenuItem menuItem) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("DELETE FORM menu_items WHERE id =?");
        statement.setString(1,Integer.toString(menuItem.getMenuItemID()));
        return statement.executeUpdate() == 1;
    }

    // Cart
    public boolean insertCart(CustomerUser customer, MenuItem menuItem) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSET INTO cart(id,customer_id,menu_item_id)");
        statement.setString(1,UUID.randomUUID().toString());
        statement.setString(2, Integer.toString(customer.getId()));
        statement.setString(3,Integer.toString(menuItem.getMenuItemID()));
        return statement.executeUpdate() == 1;
    }
    public HashMap<Integer,TreeSet<CartItem>> readCarts() throws SQLException{
        HashMap<Integer, TreeSet<CartItem>> carts = new HashMap<>();
        List<MenuItem> menuItems = readMenuItems();
        ResultSet results = connection.createStatement().executeQuery("SELECT * FROM cart");
        while(results.next()){
            int customerID = Integer.parseInt(results.getString(2).trim());
            int menuItemID = Integer.parseInt(results.getString(3).trim());
            if(!carts.containsKey(customerID)){
                carts.put(customerID,new TreeSet<>(new ComparatorMenuItem()));
                carts.get(customerID).add(new CartItem(menuItems.stream()
                        .filter(e -> e.getMenuItemID() == menuItemID)
                        .collect(Collectors.toList()).get(0),1));
            }else{
                for(CartItem c: carts.get(menuItemID)){
                    if(c.getItem().getMenuItemID() == menuItemID){
                        CartItem c2 = c;
                        c2.setQuantity(c.getQuantity()+1);
                        carts.get(customerID).add(c2);
                        carts.get(customerID).remove(c);
                        break;
                    }
                }
            }
        }
        return carts;
    }
    public boolean removeCart(CustomerUser customer) throws SQLException {
        PreparedStatement statement= connection.prepareStatement("DELETE FROM cart WHERE customer_id = ?");
        statement.setString(1,Integer.toString(customer.getId()));
        return statement.executeUpdate() == 1;
    }
    public boolean removeItemFromCart(CustomerUser customerUser,MenuItem menuItem) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM cart WHERE customer_id = ? AND menu_item_id = ?");
        statement.setString(1,Integer.toString(customerUser.getId()));
        statement.setString(2,Integer.toString(menuItem.getMenuItemID()));

        return statement.executeUpdate() == 1;
    }
}
