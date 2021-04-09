public class DrinkItem extends Item {
  private boolean containsAlcohool;

  public DrinkItem(String itemName, String itemDescription, boolean containsAlcohool) {
    super(itemName, itemDescription);
    this.setContainsAlcohool(containsAlcohool);
  }

  public boolean isContainsAlcohool() {
    return containsAlcohool;
  }

  public void setContainsAlcohool(boolean containsAlcohool) {
    this.containsAlcohool = containsAlcohool;
  }
}
