import java.util.Scanner;

public class App {

    public static void main(String[] args) throws Exception {
        Service services = new Service();

        Scanner in = new Scanner(System.in);
        int option;
        while (true) {
            showCommands();
            option = in.nextInt();
            switch (option) {
            case 0:
                in.close();
                return;
            case 1:
                services.addRestaurant();
                break;
            case 2:
                services.showAllRestaurants();
                break;
            case 3:
                services.addUser();
                break;
            case 4:
                services.showUsers();
                break;
            case 5:
                services.addItem();
                break;
            case 6:
                services.showItems();
                break;
            case 7:
                services.addItemToMenu();
                break;
            case 8:
                services.loginUser();
                break;
            case 9:
                services.addItemToCart();
                break;
            case 10:
                services.showCartItems();
                break;
            case 11:
                services.placeOrder();
                break;
            case 12:
                services.showOrdersForUser();
                break;
            case 13:
                services.showMenu();
                break;
            default:
                System.out.print("Comanda incorecta!");
            }
        }
    }

    private static void showCommands() {
        System.out.println("Folositi comenzile urmatoare: ");
        System.out.println("0:Exit");
        System.out.println("1:Adauga restaurant");
        System.out.println("2:Afisare restaurante");
        System.out.println("3:Adaugare User");
        System.out.println("4:Afisare Useri");
        System.out.println("5:Adauga Produs");
        System.out.println("6:Afiseaza Produse");
        System.out.println("7:Adauga produs in meniu");
        System.out.println("8:Log in");
        System.out.println("9:Adauga produs in cos");
        System.out.println("10:Arata cos");
        System.out.println("11:Plaseaza comanda");
        System.out.println("12.Afiseaza comenzi");
        System.out.println("13.Afiseaza Meniu");

    }
}
