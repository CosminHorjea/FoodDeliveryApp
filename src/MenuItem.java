
public class MenuItem {
  private int MenuItemID;
  private Item item;
  private float price;
  private float discount;
  static int menuCount = 1;

  public MenuItem(Item item, float price) {
    this.setItem(item);
    this.MenuItemID = ++menuCount;
    this.price = price;
  }

  public MenuItem(Item item, float price, float discount) {
    this.setItem(item);
    this.setPrice(price);
    this.discount = discount;
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
