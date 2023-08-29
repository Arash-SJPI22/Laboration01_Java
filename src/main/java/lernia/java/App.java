package lernia.java;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        char choice;
        int[] myPrices = new int[24];
        System.out.println(Arrays.toString(myPrices));

        // Do while choice != e / E
        do {
            choice = printMenu();

            // If choice is 1-4
            if ( (Character.getNumericValue(choice) > 0) && (Character.getNumericValue(choice) <= 4) ) {
                if (choice == '1')
                    enterPrices(myPrices);
                else {
                    // if the user have not entered values in choice 1 yet.
                    if (myPrices[0] == 0)
                        System.out.println("\n<== Du måste först mata in värden under meny val 1. Gör det och återkomm sen! == >\n");
                    else {
                        if (choice == '2')
                            priceCompare(myPrices);
                    }
                }

            } else if (choice == 'e' || choice == 'E')
                System.out.println("Programmet avslutas!");
            else
                System.out.println("\n<== Felaktigt val! Läs meny valen mer noggrant! ==>\n");

        } while ((choice != 'e') && (choice != 'E'));

    }

    // Prints out the menu options and returns the first character from the user input
    public static char printMenu() {
        System.out.println("Elpriser" +
                "\n========" +
                "\n1. Inmatning" +
                "\n2. Min, Max och Medel" +
                "\n3. Sortera" +
                "\n4. Bästa Laddningstid (4h)" +
                "\n5. Inläsning från fil (./priser.csv)" +
                "\ne. Avsluta");

        System.out.print("Välj ett meny val: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.next().charAt(0);
    }


    public static void enterPrices (int[] priceList) {

        System.out.println(Arrays.toString(priceList));

        for( int i = 0; i < priceList.length; i++) {
            int price = -1;

            do {
                System.out.print("Mata in priset per timme i ören per kWh mellan tiderna - ");

                if (i < 10)
                    System.out.print("0" + i);
                else
                    System.out.print(i);
                System.out.print(" - ");

                if (i + 1 < 10)
                    System.out.print("0" + (i + 1) + ": ");
                else
                    System.out.print(i+1 + ": ");

                Scanner scanner = new Scanner(System.in);
                try {
                    price = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("<== Vänligen skriv endast in heltal! ==>");
                }

            } while (!checkPriceRange(price));

            priceList[i] = price;
        }

        System.out.println(Arrays.toString(priceList));
    }

    // Check that the input is a positive number and not to large
    public static boolean checkPriceRange(int price) {
        return (price > 0 && price < Integer.MAX_VALUE);
    }

    public static void priceCompare(int[] priceList){
        System.out.println(Arrays.toString(priceList));
    }
}