import java.util.Comparator;

public class ComparatorMenuItem implements Comparator<CartItem> {

  @Override
  public int compare(CartItem arg0, CartItem arg1) {

    return arg0.getQuantity() * arg0.getItem().getDiscountedPrice() > arg1.getQuantity()
        * arg0.getItem().getDiscountedPrice() ? 1 : -1;
  }

}
