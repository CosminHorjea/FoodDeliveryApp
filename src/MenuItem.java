
public class MenuItem {
  private int MenuItemID;
  private Item item;
  private float price;
  private float discount;
  static int menuCount = 0;

  public MenuItem(Item item, float price) {
    this.item = item;
    this.MenuItemID = ++menuCount;
    this.price = price;
  }

  public MenuItem(Item item, float price, float discount) {
    this.setItem(item);
    this.setPrice(price);
    this.discount = discount;
    this.MenuItemID = ++menuCount;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public float getPrice() {
    return price;
  }

  public float getDiscountedPrice() {
    return price - price * discount;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public float getDiscount() {
    return discount;
  }

  public void setDiscount(float discount) {
    this.discount = discount;
  }

  public int getMenuItemID() {
    return MenuItemID;
  }

  @Override
  public String toString() {
    return "Item: " + item.getItemName() + " Pret: " + price;
  }

}
