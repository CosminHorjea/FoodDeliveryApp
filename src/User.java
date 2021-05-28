
public class User {
  protected int id;
  protected String username;
  protected String password;
  protected String phoneNumber;
  static int usersCount = 0;



  User(String username, String password, String phoneNumber) {
    this.id = usersCount++;
    this.username = username;
    this.password = Integer.toString(password.hashCode());
    this.phoneNumber = phoneNumber;
  }
  User(int id,String username, String password, String phoneNumber) {
    this.id = id;
    this.username = username;
    this.password = Integer.toString(password.hashCode());
    this.phoneNumber = phoneNumber;
    if(id >= usersCount){
      usersCount = id+1;
    }
  }

  public static User createUser(String[] values) {
    User user;
    if (values.length == 3) {
      user = new CustomerUser(values);
    } else {
      user = new DeliveryUser(values);
    }
    return user;
  }

  public int getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }
  public boolean verifyPassword(String passwordToTry) {
    System.out.println("Verific : "+passwordToTry);
    System.out.println(Integer.toString(passwordToTry.hashCode()));
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
