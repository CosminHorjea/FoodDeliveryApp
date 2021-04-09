public class FoodItem extends Item {
  private boolean isVegan;

  public FoodItem(String itemName, String itemDescription, boolean isVegan) {
    super(itemName, itemDescription);
    this.setVegan(isVegan);
  }

  public boolean isVegan() {
    return isVegan;
  }

  public void setVegan(boolean isVegan) {
    this.isVegan = isVegan;
  }
}
