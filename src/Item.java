
public abstract class Item {

  private int itemID;
  private String itemName;
  private String itemDescription;
  static int itemsCount = 0;

  public Item(String itemName, String itemDescription) {
    this.itemID = ++itemsCount;
    this.setItemName(itemName);
    this.setItemDescription(itemDescription);
  }

  public String getItemDescription() {
    return itemDescription;
  }

  public void setItemDescription(String itemDescription) {
    this.itemDescription = itemDescription;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public int getItemID() {
    return itemID;
  }

  @Override
  public String toString() {
    return "Item ID: " + itemID + " Item Name: " + itemName + "\n";
  }
}
