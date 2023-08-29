package lernia.java;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        char choice;

        do {
            choice = printMenu();

        } while ((choice != 'e') && (choice != 'E'));

    }

    public static char printMenu() {
        System.out.println("Elpriser" +
                "\n========" +
                "\n1. Inmatning" +
                "\n2. Min, Max och Medel" +
                "\n3. Sortera" +
                "\n4. Bästa Laddningstid (4h)" +
                "\n5. Inläsning från fil (./priser.csv)" +
                "\ne. Avsluta");

        System.out.println("Välj ett meny val: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.next().charAt(0);
    }
}