
public class User {
  protected int id;
  protected String username;
  protected String password;
  protected String phoneNumber;
  static int usersCount = 0;

  User(String username, String password, String phoneNumber) {
    this.id = ++usersCount;
    this.username = username;
    this.password = Integer.toString(password.hashCode());
    this.phoneNumber = phoneNumber;
  }

  public int getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public boolean verifyPassword(String passwordToTry) {
    passwordToTry = Integer.toString(passwordToTry.hashCode());
    if (this.password.equals(passwordToTry)) {
      return true;
    }
    return false;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public String toString() {
    return "User ID: " + id + " Username: " + username + " Password: " + password + " Telefon: " + phoneNumber + " \n";
  }
}
