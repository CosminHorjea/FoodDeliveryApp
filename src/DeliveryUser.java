public class DeliveryUser extends User {
  String licensePlate;

  public DeliveryUser(String username, String password, String phoneNumber, String licensePlate) {
    super(username, password, phoneNumber);
    this.licensePlate = licensePlate;
  }

  @Override
  public String toString() {
    return "Livrator ID: " + id + " Username: " + username + " Parola: " + password + " Telefon: " + phoneNumber
        + " Numar Inmatriculare: " + licensePlate;
  }
}
