public class FoodItem extends Item {
  private boolean isVegan;

  public FoodItem(String itemName, String itemDescription, boolean isVegan) {
    super(itemName, itemDescription);
    this.setVegan(isVegan);
  }

  public FoodItem(String[] values) {
    super(values[0], values[1]);
    if (values[2].equals("true")) {
      isVegan = true;
    } else {
      isVegan = false;
    }
  }

  public boolean isVegan() {
    return isVegan;
  }

  public void setVegan(boolean isVegan) {
    this.isVegan = isVegan;
  }
}
