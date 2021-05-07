public class DrinkItem extends Item {
  private boolean containsAlcohool;

  public DrinkItem(String itemName, String itemDescription, boolean containsAlcohool) {
    super(itemName, itemDescription);
    this.setContainsAlcohool(containsAlcohool);
  }

  public DrinkItem(String[] values) {
    super(values[0], values[1]);
    this.containsAlcohool = values[2].equals("true");
  }

  public boolean isContainsAlcohool() {
    return containsAlcohool;
  }

  public void setContainsAlcohool(boolean containsAlcohool) {
    this.containsAlcohool = containsAlcohool;
  }
}
