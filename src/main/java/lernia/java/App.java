package lernia.java;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        char choice;
        boolean dataPopulated = false;
        // Creates a PriceList array and initiates it.
        int[][] priceList = new int[24][2];

        for(int i=0; i<priceList.length; i++) {
            priceList[i][0] = i;
        }

        // Do while choice != e / E
        do {
            choice = printMenu();

            // If choice is 1-4
            if ( (Character.getNumericValue(choice) > 0) && (Character.getNumericValue(choice) <= 4) ) {
                if (choice == '1') {
                    enterPrices(priceList);
                    dataPopulated = true;
                }
                else {
                    // if the user have not entered values in choice 1 or 5 yet.
                    if (!dataPopulated)
                        System.out.println("\n<== Du måste först mata in värden under meny val 1 eller 5. Gör det och återkom sen! == >\n");
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
                try {
                    if (readFromFile(priceList)) {
                        dataPopulated = true;
                        System.out.println("\n<== Fil \"priser.csv\" inläst utan problem ==>");
                    } else
                        System.out.println("\n<== Fel vid inläsning av fil, kontrollera att filens innehåll är rätt formaterad ==>\n");

                } catch (IOException e) {
                    System.out.println("\n<== Fil ej inläst. Problem uppstod: " + e + " ==>\n");
                }

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
                5. Inläsning från fil (priser.csv)
                e. Avsluta
                """);

        System.out.print("Välj ett meny val: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.next().charAt(0);
    }


    // Menu option 1
    public static void enterPrices (int[][] pList) {

        for( int i = 0; i < pList.length; i++) {
            int price = -1;

            do {
                System.out.print("Mata in priset per timme i ören per kWh mellan tiderna - " + sanitizeHourOutput(pList[i][0]));

                Scanner scanner = new Scanner(System.in);
                try {
                    price = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("<== Vänligen skriv endast in heltal! ==>");
                }

            } while (!checkPriceRange(price));

            pList[i][1] = price;
        }
    }


    // Menu option 2
    public static void priceCompare(int[][] pList){
        int min = Integer.MAX_VALUE;
        int minHour = -1;
        int max = 0;
        int maxHour = -1;
        int avrg = 0;

        for (int i = 0; i < pList.length; i++) {

            avrg += pList[i][1];
            // Exception . if two or more prices are equal the last one in the array will be set as the max and that hour will be set as that (same for min)
            if (Math.max(max, pList[i][1]) == pList[i][1]) {
                maxHour = pList[i][0];
                max = pList[i][1];
            }

            if (Math.min(min, pList[i][1]) == pList[i][1]) {
                minHour = pList[i][0];
                min = pList[i][1];
            }
        }

        System.out.println("\nDet högsta priset under dygnet var: " + max + " öre per kWh och skedde mellan kl " + sanitizeHourOutput(maxHour));
        System.out.println("Det lägsta priset under dygnet var: " + min + " öre per kWh och skedde mellan kl " + sanitizeHourOutput(minHour));
        System.out.println("Medel priset under dygnet var: " + (avrg / pList.length) + " öre per kWh\n");
    }

    // Menu option 3
    public static void sortMyPrices (int[][] pList) {

        int[][] sortedList = new int[24][2];

        for (int i=0; i<pList.length; i++) {
            sortedList[i][1] = pList[i][1];
            sortedList[i][0] = pList[i][0];
        }

        for (int i=0; i<sortedList.length; i++) {
            int tmpPrice;
            int tmpHour;

            for (int j=0; j<sortedList.length; j++) {
                if (sortedList[i][1] < sortedList[j][1]) {
                    tmpPrice = sortedList[i][1];
                    tmpHour = sortedList[i][0];
                    sortedList[i][1] = sortedList[j][1];
                    sortedList[i][0] = sortedList[j][0];
                    sortedList[j][1] = tmpPrice;
                    sortedList[j][0] = tmpHour;
                }
            }
        }

        for (int i=0; i<sortedList.length; i++) {
            System.out.println(sanitizeHourOutput(sortedList[i][0]) + " " + sortedList[i][1] + " öre");
        }
    }

    // Menu option 4
    public static void bestChargingTime(int[][] pList) {
        int bestPrice = Integer.MAX_VALUE;
        int bestTime = 0;

        for (int i=0; i<(pList.length - 3); i++) {

            int price = pList[i][1] + pList[i+1][1] + pList[i+2][1] + pList[i+3][1];

            if (price < bestPrice) {
                bestTime = pList[i][0];
                bestPrice = price;
            }
        }

        System.out.print("\nBästa tiden att börja ladda är kl ");

        if (bestTime < 10)
            System.out.print("0" + bestTime);
        else
            System.out.print(bestTime);

        System.out.println(", medelpriset blir då ca: " + (bestPrice / 4) + " öre kWh.\n");
    }

    // Menu option 5
    public static boolean readFromFile (int[][] pList) throws IOException {

        boolean fileReadCorrectly = true;
        Scanner fileStream = new Scanner(new File("./src/main/java/lernia/java/priser.csv"));
        fileStream.useDelimiter(",");

        for (int i=0; i<pList.length; i++) {
            try {
                pList[i][0] = fileStream.nextInt();
                pList[i][1] = fileStream.nextInt();
            } catch (Exception e) {
                fileReadCorrectly = false;
            }
        }
        return fileReadCorrectly;
    }

    // Misc methods

    // For option 2 - Check that the input is a positive number and not to large
    public static boolean checkPriceRange(int price) {
        return (price > 0 && price < 10000000);
    }

    // Adds an 0 before the hour if it is less than 10. Returns a string in the format "01 - 02"
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