import java.util.TreeSet;

/**
 * Order
 */
public class Order {
  private int OrderID;
  private TreeSet<CartItem> items;
  private boolean delivered = false;
  private CustomerUser customer;
  private DeliveryUser delivery;
  static int ordersCount = 0;

  public Order(TreeSet<CartItem> cart, CustomerUser customer, DeliveryUser delivery) {
    this.OrderID = ordersCount++;
    this.setItems(cart);
    this.setCustomer(customer);
    this.setDelivery(delivery);
  }
  public Order(int id,TreeSet<CartItem> cart, CustomerUser customer, DeliveryUser delivery) {
    this.OrderID = id;
    this.setItems(cart);
    this.setCustomer(customer);
    this.setDelivery(delivery);
    if(id > ordersCount){
      ordersCount = id;
    }
  }
  public Order(int id,TreeSet<CartItem> cart, CustomerUser customer, DeliveryUser delivery,boolean delivered) {
    this.OrderID = id;
    this.setItems(cart);
    this.setCustomer(customer);
    this.setDelivery(delivery);
    if(id > ordersCount){
      ordersCount = id;
    }
    this.delivered= delivered;
  }
  public boolean isDelivered() {
    return delivered;
  }

  public void setDelivered(boolean delivered) {
    this.delivered = delivered;
  }

  public TreeSet<CartItem> getItems() {
    return items;
  }

  public void setItems(TreeSet<CartItem> items) {
    this.items = items;
  }

  public DeliveryUser getDelivery() {
    return delivery;
  }

  public void setDelivery(DeliveryUser delivery) {
    this.delivery = delivery;
  }

  public CustomerUser getCustomer() {
    return customer;
  }

  public void setCustomer(CustomerUser customer) {
    this.customer = customer;
  }

  public void completeOrder() {
    setDelivered(true);
  }

  public int getOrderID() {
    return OrderID;
  }

  @Override
  public String toString() {
    String s = "";
    s += "Comanda :" + OrderID;
    s += "\nClient: " + customer.id;
    s += "\nLivrator: " + delivery.id + " | " + delivery.getLicensePlate();
    for (CartItem item : items) {
      s += item.getItem().toString() + "\n";
    }
    return s;
  }
}