//Rezvan Nafee
//112936468
//Recitation 04

import java.io.*
import java.util.*;

/**
 * This class represents the main driver for the HashedGrocery. In this class, it allows the user to mange/use the
 * function offered by HashedGrocery.
 *
 * @author Rezvan Nafee
 * @ID 112936468
 * @Recitation Recitation 04
 */

public class GroceryDriver {

    /**
     * This the main method of the program that will begin by checking if a HashGrocery object exists by the name of
     * grocery.obj. If it does not exist, then the program will initialize a new HashedGrocery that will be serialized
     * upon termination of the program. If it does exist, then the program will load the HashedGrocery object. After
     * that the user will be presented with a menu of options that correspond to different functions that the inventory
     * management system can perform. Upon termination of the program, the HashedGrocery object will be serialized and
     * stored for later use if need be.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String key = "";
        HashedGrocery grocery = null;
        boolean found = false;
        try {
            FileInputStream file = new FileInputStream("grocery.obj");
            ObjectInputStream in = new ObjectInputStream(file);
            grocery = (HashedGrocery) in.readObject();
            found = true;
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("grocery.obj does not exist! Creating new HashedGrocery object...");
            grocery = new HashedGrocery();
        }
        if (found)
            System.out.println("HashedGrocery is loaded from grocery.obj!");
        System.out.println("\nBusiness Day " + grocery.getBusinessDay());
        while (!key.equals("q")) {
            printTable();
            System.out.print("\nEnter option: ");
            key = input.nextLine().trim().toLowerCase();
            switch (key) {
                case ("l"):
                    String fileName = "";
                    System.out.print("\nEnter file to load: ");
                    fileName = input.nextLine().trim();
                    try {
                        grocery.addItemCatalog(fileName);
                    } catch (IOException | InvalidJSONFile e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                    break;
                case ("a"):
                    String itemCode = "";
                    String itemName = "";
                    int quantity = 0;
                    int avgSales = 0;
                    double price = 0.0;
                    System.out.print("\nEnter item code: ");
                    itemCode = input.nextLine().trim();
                    if (itemCode.isEmpty()) {
                        System.out.print("Enter name: ");
                        input.nextLine();
                        System.out.print("Enter average sales per day: ");
                        input.nextLine();
                        System.out.print("Enter price: ");
                        input.nextLine();
                        System.out.println("\nAdding Item Error: Invalid information given!");
                        continue;
                    }
                    System.out.print("Enter name: ");
                    itemName = input.nextLine().trim();
                    if (itemName.isEmpty()) {
                        System.out.print("Enter average sales per day: ");
                        input.nextLine();
                        System.out.print("Enter price: ");
                        input.nextLine();
                        System.out.println("\nAdding Item Error: Invalid information given!");
                        continue;
                    }
                    try {
                        System.out.print("Enter quantity in store: ");
                        quantity = input.nextInt();
                        input.nextLine();
                    } catch (InputMismatchException ex) {
                        input.nextLine();
                        System.out.print("Enter average sales per day: ");
                        input.nextLine();
                        System.out.print("Enter price: ");
                        input.nextLine();
                        System.out.println("\nAdding Item Error: Invalid information given!");
                        continue;
                    }
                    try {
                        System.out.print("Enter average sales per day: ");
                        avgSales = input.nextInt();
                        input.nextLine();
                    } catch (InputMismatchException ex) {
                        input.nextLine();
                        System.out.print("Enter price: ");
                        input.nextLine();
                        System.out.println("\nAdding Item Error: Invalid information given!");
                        continue;
                    }
                    try {
                        System.out.print("Enter price: ");
                        price = input.nextDouble();
                        input.nextLine();
                    } catch (InputMismatchException ex) {
                        input.nextLine();
                        System.out.println("\nAdding Item Error: Invalid information given!");
                        continue;
                    }
                    Item item = new Item(itemCode, itemName, quantity, avgSales, price);
                    try {
                        grocery.addItem(item);
                        System.out.println("\n" + item.getItemCode() + ": " + item.getName() + " added to inventory!");
                    } catch (DuplicateItem ex) {
                        System.out.println(ex.getLocalizedMessage());
                        continue;
                    }
                    break;
                case ("b"):
                    System.out.print("\nEnter file to load: ");
                    fileName = input.nextLine().trim();
                    try {
                        grocery.processSales(fileName);
                    } catch (IOException | InvalidJSONFile e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                    break;
                case ("c"):
                    System.out.println(grocery.toString());
                    break;
                case ("n"):
                    grocery.nextBusinessDay();
                    break;
                case ("q"):
                    try {
                        FileOutputStream file = new FileOutputStream("grocery.obj");
                        ObjectOutputStream fileOut = new ObjectOutputStream(file);
                        fileOut.writeObject(grocery);
                        fileOut.close();
                    } catch (IOException ex) {
                    }
                    System.out.println("\nHashedGrocery is saved in grocery.obj!");
                    System.out.println("\nProgram terminating normally...");
                    break;
                default:
                    System.out.println("\nNot a valid option!");
            }
        }
    }

    /**
     * This method prints out the option that the user can perform with the inventory management system or the
     * HashedGrocery object.
     */
    public static void printTable() {
        System.out.println("\nMenu:");
        System.out.println("(L) Load item catalog");
        System.out.println("(A) Add items");
        System.out.println("(B) Process Sales");
        System.out.println("(C) Display all items");
        System.out.println("(N) Move to next business day");
        System.out.println("(Q) Quit");
    }
}
