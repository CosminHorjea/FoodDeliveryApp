import java.util.List;

/**
 * Order
 */
public class Order {
  private int OrderID;
  private List<CartItem> items;
  private boolean delivered = false;
  private int CustomerID;
  private int DeliveryID;
  static int ordersCount = 0;

  public Order(List<CartItem> cart, int CustomerID, int DeliveryID) {
    this.OrderID = ++ordersCount;
    this.items = cart;
    this.CustomerID = CustomerID;
    this.DeliveryID = DeliveryID;
  }

  public void completeOrder() {
    delivered = true;
  }
}