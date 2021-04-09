import java.util.ArrayList;
import java.util.List;

public class CustomerUser extends User {
  // ?? MAybe do a cart class
  List<CartItem> cart;

  public CustomerUser(String username, String password, String phoneNumber) {
    super(username, password, phoneNumber);
    this.cart = new ArrayList<CartItem>();
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

  public float getCartPrice() {
    float totalPrice = 0.0f;
    for (CartItem c : cart) {
      totalPrice += (c.getItem().getPrice() - c.getItem().getPrice() * c.getItem().getDiscount()) * c.getQuantity();
    }
    return totalPrice;
  }

  public List<CartItem> getCart() {
    return cart;
  }
}
