package lernia.java;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        char choice;

        PriceList[] priceList = new PriceList[24];
        for (int i=0; i<priceList.length; i++) {
            priceList[i] = new PriceList();
            priceList[i].hour = i;
        }

        // Do while choice != e / E
        do {
            choice = printMenu();

            // If choice is 1-4
            if ( (Character.getNumericValue(choice) > 0) && (Character.getNumericValue(choice) <= 4) ) {
                if (choice == '1')
                    enterPrices(priceList);
                else {
                    // if the user have not entered values in choice 1 yet.
                    if (priceList[0].price == 0)
                        System.out.println("\n<== Du måste först mata in värden under meny val 1. Gör det och återkomm sen! == >\n");
                    else {
                        if (choice == '2')
                            priceCompare(priceList);
                        else if (choice == '3')
                            sortMyPrices(priceList);
                        else if (choice == '4')
                            bestChargingTime(priceList);

                    }
                }

            } else if (choice == '5') {
                System.out.println("Val 5");

            } else if (choice == 'e' || choice == 'E')
                System.out.println("\n<== Programmet avslutas! ==>");
            else
                System.out.println("\n<== Felaktigt val! Läs meny valen mer noggrant! ==>\n");

        } while ((choice != 'e') && (choice != 'E'));

    }

    // Prints out the menu options and returns the first character from the user input
    public static char printMenu() {
        System.out.println("""
                
                Elpriser
                ========
                1. Inmatning
                2. Min, Max och Medel
                3. Sortera
                4. Bästa Laddningstid (4h)
                5. Inläsning från fil (./priser.csv)
                e. Avsluta
                """);

        System.out.print("Välj ett meny val: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.next().charAt(0);
    }


    // Menu option 1
    public static void enterPrices (PriceList[] pList) {

        for( int i = 0; i < pList.length; i++) {
            int price = -1;

            do {
                System.out.print("Mata in priset per timme i ören per kWh mellan tiderna - " + sanitizeHourOutput(pList[i].hour));

                Scanner scanner = new Scanner(System.in);
                try {
                    price = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("<== Vänligen skriv endast in heltal! ==>");
                }

            } while (!checkPriceRange(price));

            pList[i].price = price;
        }
    }


    // Menu option 2
    public static void priceCompare(PriceList[] pList){
        int min = Integer.MAX_VALUE;
        int minHour = -1;
        int max = 0;
        int maxHour = -1;
        int avrg = 0;

        for (int i = 0; i < pList.length; i++) {

            avrg += pList[i].price;
            // Exception . if two or more prices are equal the last one in the array will set as the max and that hour will be set as that (same for min)
            if (Math.max(max, pList[i].price) == pList[i].price) {
                maxHour = i;
                max = pList[i].price;
            }

            if (Math.min(min, pList[i].price) == pList[i].price) {
                minHour = i;
                min = pList[i].price;
            }
        }

        System.out.println("\nDet högsta priset under dynget var: " + max + " öre per kWh och skedde mellan kl " + sanitizeHourOutput(maxHour));
        System.out.println("Det lägsta priset under dynget var: " + min + " öre per kWh och skedde mellan kl " + sanitizeHourOutput(minHour));
        System.out.println("Medel priset under dynget var: " + (avrg / pList.length) + " öre per kWh\n");


    }

    // Menu option 3
    public static void sortMyPrices (PriceList[] pList) {

        PriceList[] sortedList = new PriceList[24];

        for (int i=0; i<pList.length; i++) {
            sortedList[i] = new PriceList();
            sortedList[i].price = pList[i].price;
            sortedList[i].hour = pList[i].hour;
        }

        for (int i=0; i<sortedList.length; i++) {
            int tmpPrice;
            int tmpHour;

            for (int j=0; j<sortedList.length; j++) {
                if (sortedList[i].price < sortedList[j].price) {
                    tmpPrice = sortedList[i].price;
                    tmpHour = sortedList[i].hour;
                    sortedList[i].price = sortedList[j].price;
                    sortedList[i].hour = sortedList[j].hour;
                    sortedList[j].price = tmpPrice;
                    sortedList[j].hour = tmpHour;
                }
            }
        }

        for (int i=0; i<sortedList.length; i++) {
            System.out.println(sanitizeHourOutput(sortedList[i].hour) + " " + sortedList[i].price + " öre");
        }
    }

    // Menu option 4
    public static void bestChargingTime(PriceList[] pList) {
        int bestPrice = Integer.MAX_VALUE;
        int bestTime = 0;

        for (int i=0; i<(pList.length - 3); i++) {

            int price = pList[i].price + pList[i+1].price + pList[i+2].price + pList[i+3].price;

            if (price < bestPrice) {
                bestTime = pList[i].hour;
                bestPrice = price;
            }
        }

        System.out.print("\nBästa tiden att börja ladda är kl ");

        if (bestTime < 10)
            System.out.print("0" + bestTime);
        else
            System.out.print(bestTime);

        System.out.println(", medelpriset blir då ca: " + bestPrice / 4 + " öre kWh.\n");
    }

    // Misc methods

    // For option 2 - Check that the input is a positive number and not to large
    public static boolean checkPriceRange(int price) {
        return (price > 0 && price < 10000000);
    }
    public static String sanitizeHourOutput (int hour) {
        String time;
        if (hour < 10)
            time = "0" + hour;
        else
            time = "" + hour;

        time += "-";

        if (hour + 1 < 10)
            time += "0" + (hour + 1) + ": ";
        else
            time += (hour + 1) + ": ";

        return time;
    }
}
