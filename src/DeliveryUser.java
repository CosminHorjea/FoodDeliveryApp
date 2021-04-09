public class DeliveryUser extends User {
  private String licensePlate;
  private boolean isDelivering = false;

  public DeliveryUser(String username, String password, String phoneNumber, String licensePlate) {
    super(username, password, phoneNumber);
    this.licensePlate = licensePlate;
  }

  public boolean isDelivering() {
    return isDelivering;
  }

  public void setDelivering(boolean isDelivering) {
    this.isDelivering = isDelivering;
  }

  public String getLicensePlate() {
    return licensePlate;
  }

  public void assignOrder() {
    setDelivering(true);
  }

  public void completeOrder() {
    setDelivering(false);
  }

  @Override
  public String toString() {
    return "Livrator ID: " + id + " Username: " + username + " Parola: " + password + " Telefon: " + phoneNumber
        + " Numar Inmatriculare: " + licensePlate;
  }
}
