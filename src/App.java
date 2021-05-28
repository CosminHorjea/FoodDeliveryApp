import java.util.Scanner;

public class App {
    private static Service services;

    public static void main(String[] args) throws Exception {
        services = Service.getInstance();
//        new LoginScreen();
        Scanner in = new Scanner(System.in);
        int option;
        while (true) {
            showCommands();
            option = in.nextInt();
            switch (option) {
                case 0:
                    in.close();
                    services.closeAudit();
                    return;
                case 1:
                    services.loginUser();
                    break;
                case 2:
                    services.addUser();
                    break;
                case 3:
                    services.logoutUser();
                    break;
                case 4:
                    services.showAllRestaurants();
                    break;
                case 5:
                    services.showMenu();
                    break;
                case 6:
                    services.addItemToCart();
                    break;
                case 7:
                    services.showCartItems();
                    break;
                case 8:
                    services.placeOrder();
                    break;
                case 9:
                    services.showOrdersForUser();
                    break;
                case 10:
                    services.showOrederToDeliver();
                    break;
                case 11:
                    services.completeCurrentDelivery();
                    break;
                case 20:
                    services.showMenuItems();
                    break;
                default:
                    System.out.print("Comanda incorecta!");
            }
        }
    }

    private static void showCommands() {
        System.out.println("------------------------------");
        System.out.println("Folositi comenzile urmatoare: ");
        System.out.println("0:Exit");
        if (!services.isLoggedIn()) {
            System.out.println("1:Log in");
            System.out.println("2:Inregistrare User");
        } else {
            System.out.println("3: Log Out");
            System.out.println("4:Afisare restaurante");
            System.out.println("5.Afiseaza Meniu");
            if (services.isCustomerLoggedIn()) {
                System.out.println("6:Adauga produs in cos");
                System.out.println("7:Arata cos");
                System.out.println("8:Plaseaza comanda");
                System.out.println("9.Afiseaza comenzi");
            }
            if (services.isDeliveryLoggedIn()) {
                System.out.println("10.Afiseaza comanda curenta");
                System.out.println("11.Completeaza comanda curenta");
            }
        }

    }
}
