//Rezvan Nafee
//112936468
//Recitation 04

import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

/**
 * This class represent a grocery store known as a HashedGrocery. The HashedGrocery will help manage/process Items that
 * are currently in the inventory. Functions such as updating the stock of the Item, processing sales of Items, and
 * adding Items to the inventory of the HashedGrocery. As its name suggests, the Items in the inventory are all stored
 * in a HashTable.
 *
 * @author Rezvan Nafee
 * @ID 112936468
 * @Recitation Recitation 04
 */

public class HashedGrocery implements Serializable {
    private int businessDay = 1;
    private Hashtable<String, Item> hashtable = new Hashtable<>();

    /**
     * Constructs the HashedGrocery object with no-args.
     */
    public HashedGrocery() {
    }

    /**
     * Updates the stock of the an Item by a specified quantity.
     *
     * @param item        The Item that needs to be updated.
     * @param adjustByQty The specified quantity to update the Item by.
     */
    public void updateItem(Item item, int adjustByQty) {
        hashtable.get(item.getItemCode()).setQtyInStore(hashtable.get(item.getItemCode()).getQtyInStore() - adjustByQty);
    }

    /**
     * Adds an Item to a the the HashTable by using the Item's item code. If the Item's item code already exists in the
     * HashTable, then an exception is thrown and the Item is not added to the HashTable.
     *
     * @param item The Item to be added to the HashTable.
     * @throws DuplicateItem Thrown if the Item's item code exists in the HashTable.
     */
    public void addItem(Item item) throws DuplicateItem {
        if (!hashtable.containsKey(item.getItemCode())) {
            hashtable.put(item.getItemCode(), item);
        } else {
            throw new DuplicateItem("\n" + item.getItemCode() + ": Cannot add item as item code already exists!");
        }
    }

    /**
     * Adds all Items present within a JSON text file. If the Item that is trying to be added to the the HashTable
     * already has an existing item code key, then the Item is not added to the HashTable. In addition if the text
     * file that is being parsed is not in the JSON format, then an exception is thrown. If the file does not exist,
     * then an exception is also thrown.
     *
     * @param filename The file that contains a list of Items.
     * @throws IOException     Thrown if the file does not exist or cannot be found.
     * @throws InvalidJSONFile Thrown if the file being parsed is not in the JSON format.
     */
    public void addItemCatalog(String filename) throws IOException, InvalidJSONFile {
        try {
            FileInputStream inFile = new FileInputStream(filename);
            System.out.println();
            InputStreamReader inputStreamReader = new InputStreamReader(inFile);
            JSONParser jsonParser = new JSONParser();
            JSONArray objects = (JSONArray) jsonParser.parse(inputStreamReader);
            for (int i = 0; i < objects.size(); i++) {
                JSONObject jsonObject = (JSONObject) objects.get(i);
                Item newItem = new Item((String) jsonObject.get("itemCode"), (String) jsonObject.get("itemName"),
                        Integer.parseInt((String) jsonObject.get("qtyInStore")),
                        Integer.parseInt((String) jsonObject.get("avgSales")),
                        Double.parseDouble((String) jsonObject.get("price")));
                newItem.setQtyInStore(Integer.parseInt((String) jsonObject.get("qtyInStore")));
                newItem.setOnOrder(Integer.parseInt((String) jsonObject.get("amtOnOrder")));
                try {
                    addItem(newItem);
                } catch (DuplicateItem ex) {
                    System.out.println(newItem.getItemCode() + ": Cannot add as item code already exists in the " +
                            "grocery!");
                    continue;
                }
                System.out.println(newItem.getItemCode() + ": " + newItem.getName() + " added to inventory!");
            }
        } catch (IOException ex) {
            throw new IOException("\nAdding Catalog Error: Could not find catalog by the name of " + filename + "!");
        } catch (ParseException ex) {
            throw new InvalidJSONFile("Adding Catalog Error: " + filename + " is an invalid catalog!");
        }
    }

    /**
     * This method process the sales within the the HashedGrocery by reading a JSON text file tht contains information
     * regarding the Item's item code and the amount of the Item sold hat day. If the item code exists in the HashTable,
     * the stock of the item is updated depending on the amount of that was sold. Next the method will check if there is
     * enough stock left for 3 days. If there isn't, the method orders more of that Item. In addition in prints what has
     * been sold, what has been ordered, and if the item does not exist in the store. If file does not exist or cannot
     * be found, an error is thrown. If the file cannot be parsed, then an error is thrown.
     *
     * @param filename The name of the JSON file that will tell us the sales that were made that day.
     * @throws IOException     Thrown if the file does not exist or cannot be found.
     * @throws InvalidJSONFile Thrown if the file being parsed is not in the JSON format.
     */
    public void processSales(String filename) throws IOException, InvalidJSONFile {
        try {
            FileInputStream inFile = new FileInputStream(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(inFile);
            JSONParser jsonParser = new JSONParser();
            JSONArray objects = (JSONArray) jsonParser.parse(inputStreamReader);
            for (int i = 0; i < objects.size(); i++) {
                if (i == 0)
                    System.out.println();
                JSONObject object = (JSONObject) objects.get(i);
                Item item = hashtable.get(object.get("itemCode"));
                if (item == null) {
                    System.out.println(object.get("itemCode") + ": Cannot buy as it is not in the grocery store!");
                    continue;
                } else {
                    if (Integer.parseInt((String) object.get("qtySold")) > item.getQtyInStore()) {
                        System.out.println(item.getItemCode() + ": Not enough stock for sale! Not updated.");
                        continue;
                    } else {
                        updateItem(item, Integer.parseInt((String) object.get("qtySold")));
                        System.out.print(item.getItemCode() + ": " + object.get("qtySold") + " units of " +
                                item.getName() + " are sold!");
                        if (item.getQtyInStore() < item.getAverageSalesPerDay() * 3) {
                            if (item.isOrder()) {
                                System.out.println(" An order has been already placed for more " + item.getName()
                                        + "!");
                                continue;
                            } else {
                                int updateItemBy = item.getAverageSalesPerDay() * 2;
                                System.out.print(" Order has been placed for " + updateItemBy + " more units.\n");
                                item.setOnOrder(updateItemBy);
                                item.setOrder(true);
                                item.setArrivalDay(businessDay + 3);
                            }
                        } else {
                            System.out.print("\n");
                        }
                    }
                }
            }
        } catch (IOException ex) {
            throw new IOException("\nProcessing Sales Error: Could not find file by the name of " + filename + "!");
        } catch (ParseException ex) {
            throw new InvalidJSONFile("\nProcessing Sales Error: " + filename + " cannot be processed!");
        }
    }

    /**
     * Advances the business day by 1 and then displays information regarding if any orders that have arrived. If
     * orders have arrived, then the method prints out the number of units that have arrived for the particular Item
     * and updates the quantity of the Item, the boolean flag of has it been ordered, and the arrival day and ordered
     * amount of the Item to 0. However if there no orders have arrived, then the method displays nothing has arrived.
     */
    public void nextBusinessDay() {
        setBusinessDay(businessDay + 1);
        System.out.println("\nAdvancing business day...");
        System.out.println("Business Day " + businessDay + ".");
        Object[] itemKeys = hashtable.keySet().toArray();
        boolean receivedOrders = false;
        ArrayList<Item> receivedItems = new ArrayList<>();
        System.out.println();
        for (int i = 0; i < hashtable.size(); i++) {
            if (hashtable.get(String.valueOf(itemKeys[i])).getArrivalDay() == businessDay) {
                receivedOrders = true;
                receivedItems.add(hashtable.get(String.valueOf(itemKeys[i])));
            }
        }
        if (!receivedOrders) {
            System.out.println("No orders have arrived!");
        } else {
            System.out.println("Orders have arrived for: ");
            for (Item item : receivedItems) {
                System.out.println(item.getItemCode() + ": " + item.getOnOrder() + " units of " + item.getName() + "!");
                item.setQtyInStore(item.getQtyInStore() + item.getOnOrder());
                item.setOnOrder(0);
                item.setArrivalDay(0);
                item.setOrder(false);
            }
        }
    }

    /**
     * Return that HashTable that is being used as the inventory of the grocery.
     *
     * @return The inventory of the HashedGrocery.
     */
    public Hashtable<String, Item> getHashtable() {
        return hashtable;
    }

    /**
     * Returns the business day of the grocery is currently in.
     *
     * @return The business day.
     */
    public int getBusinessDay() {
        return businessDay;
    }

    /**
     * Sets the business day to a specified day.
     *
     * @param businessDay The specified business day.
     */
    public void setBusinessDay(int businessDay) {
        this.businessDay = businessDay;
    }

    /**
     * Sets the inventory of the HashedGrocery to a specified inventory or HashTable.
     *
     * @param hashtable The specified inventory.
     */
    public void setHashtable(Hashtable<String, Item> hashtable) {
        this.hashtable = hashtable;
    }

    /**
     * Returns the String representation of the HashedGrocery by retuning a formatted table that displays inventory. In
     * other words, the Items that are currently in the inventory.
     *
     * @return The String representation of a HashedGrocery object.
     */
    public String toString() {
        String table = "";
        String format = String.format("\n%-15s%-20s%-20s%-15s%-15s%-15s%-15s\n", "Item Code", "Name", "Qty", "AvgSales"
                , "Price", "OnOrder", "ArrOnBusDay");
        String divide = "----------------------------------------------------------------------------------------" +
                "-------------------------\n";
        table += format + divide;
        Object[] keys = hashtable.keySet().toArray();
        for (int i = 0; i < hashtable.size(); i++) {
            if (i == hashtable.size() - 1) {
                table += hashtable.get(String.valueOf(keys[i])).toString();
            } else
                table += hashtable.get(String.valueOf(keys[i])).toString() + "\n";
        }
        return table;
    }
}
