
public class CartItem {
  private MenuItem item;
  private int quantity;

  public CartItem(MenuItem item, int quantity) {
    this.item = item;
    this.quantity = quantity;
  }

  public void increaseQuantity() {
    this.quantity++;
  }

  public MenuItem getItem() {
    return item;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public void setItem(MenuItem item) {
    this.item = item;
  }
}
