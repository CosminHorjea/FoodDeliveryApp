import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
  protected int id;
  protected String username;
  protected String password;
  protected String phoneNumber;
  static int usersCount = 0;

  User(String username, String password, String phoneNumber) {
    this.id = ++usersCount;
    this.username = username;
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      this.password = digest.digest(password.getBytes(StandardCharsets.UTF_8)).toString();
    } catch (NoSuchAlgorithmException e) {
      System.out.println("Error when hashing password");
    }
    this.phoneNumber = phoneNumber;
  }

  @Override
  public String toString() {
    return "User ID: " + id + " Username: " + username + " Password: " + password + " Telefon: " + phoneNumber + " \n";
  }
}
