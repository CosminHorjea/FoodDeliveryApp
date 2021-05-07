
import java.util.TreeSet;

public class CustomerUser extends User {
  TreeSet<CartItem> cart;

  public CustomerUser(String username, String password, String phoneNumber) {
    super(username, password, phoneNumber);
    this.cart = new TreeSet<>(new ComparatorMenuItem());
  }

  public CustomerUser(String[] values) {
    super(values[0], values[1], values[2]);
    this.cart = new TreeSet<>(new ComparatorMenuItem());
  }

  public void addItemToCart(MenuItem menuItem) {
    for (CartItem c : cart) {
      if (c.getItem().getMenuItemID() == menuItem.getMenuItemID()) {
        c.increaseQuantity();
        return;
      }
    }
    cart.add(new CartItem(menuItem, 1));
  }

  public void emptyCart() {
    this.cart.clear();
  }

  public float getCartPrice() {
    float totalPrice = 0.0f;
    for (CartItem c : cart) {
      totalPrice += (c.getItem().getPrice() - c.getItem().getPrice() * c.getItem().getDiscount()) * c.getQuantity();
    }
    return totalPrice;
  }

  public TreeSet<CartItem> getCart() {
    return cart;
  }
}
