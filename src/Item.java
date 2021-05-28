
public abstract class Item {

  private int itemID;
  private String itemName;
  private String itemDescription;
  static int itemsCount = 0;

  public Item(String itemName, String itemDescription) {
    this.itemID = itemsCount++;
    this.setItemName(itemName);
    this.setItemDescription(itemDescription);
  }
  public Item(int id,String itemName, String itemDescription) {
    this.itemID = id;
    this.setItemName(itemName);
    this.setItemDescription(itemDescription);
    if(id > itemsCount){
      itemsCount=id;
    }
  }
  public static Item createItem(String[] values) {
    Item item;
    if (values.length == 3) {
      item = new FoodItem(values);
    } else {
      item = new DrinkItem(values);
    }
    return item;

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
    return "Produs: " + itemName + "\n";
  }
}
