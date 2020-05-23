//Rezvan Nafee
//112936468
//Recitation 04

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * This class represents an Item that will be used in our HashedGrocery. Each item will be identified with its own
 * unique item code. In addition the Item object will contain important information such as the quantity in the store,
 * how much of the Item needs to be ordered if there is not enough in the store to sell, and the price of the Item.
 *
 * @author Rezvan Nafee
 * @ID 112936469
 * @Recitation Recitation 04
 */

public class Item implements Serializable {
    private String itemCode;
    private String name;
    private int qtyInStore;
    private int averageSalesPerDay;
    private int onOrder;
    private int arrivalDay;
    private double price;
    private boolean order;
    private DecimalFormat df = new DecimalFormat("#.##");

    /**
     * This is a no-arg constructor that creates a default Item to be used in the HashedGrocery.
     */
    public Item() {
    }

    /**
     * Constructs a specific Item with information such as the the Item's item code, name, the amount to be in the
     * HashedGrocery, the average sales of the Item, and the price of the Item.
     *
     * @param itemCode           The unique key of the Item
     * @param name               The name of the Item
     * @param qtyInStore         The amount of the Item
     * @param averageSalesPerDay The number of sales that an Item is expected to be sold on a business day.
     * @param price              The price of the Item
     */
    public Item(String itemCode, String name, int qtyInStore, int averageSalesPerDay, double price) {
        this.itemCode = itemCode;
        this.name = name;
        this.qtyInStore = qtyInStore;
        this.averageSalesPerDay = averageSalesPerDay;
        this.price = price;
    }

    /**
     * Returns the total stock of the Item.
     *
     * @return The stock of the Item.
     */
    public int getQtyInStore() {
        return qtyInStore;
    }

    /**
     * Returns the number of sales the Item is projected to sell on a business day.
     *
     * @return The number of sales the Item is projected to sell on a business day.
     */
    public int getAverageSalesPerDay() {
        return averageSalesPerDay;
    }

    /**
     * Returns the price of the Item.
     *
     * @return The price of the Item.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the business day at which the order of the Item will arrive.
     *
     * @return The arrival day of the Item.
     */
    public int getArrivalDay() {
        return arrivalDay;
    }

    /**
     * Returns the ordered amount of the Item.
     *
     * @return The ordered amount of the Item.
     */
    public int getOnOrder() {
        return onOrder;
    }

    /**
     * Return the item code of the Item.
     *
     * @return The unique item code that identifies an Item.
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * Returns that name of the Item.
     *
     * @return The name of the Item.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the arrival day of the Item to a specific business day.
     *
     * @param arrivalDay The specified business day at which the Item will arrive.
     */
    public void setArrivalDay(int arrivalDay) {
        this.arrivalDay = arrivalDay;
    }

    /**
     * Sets the Item's item code to a specified item code.
     *
     * @param itemCode The specified item code.
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * Sets the average sales of the Item to a specific amount.
     *
     * @param averageSalesPerDay The specified amount of average sales of an Item.
     */
    public void setAverageSalesPerDay(int averageSalesPerDay) {
        this.averageSalesPerDay = averageSalesPerDay;
    }

    /**
     * Sets the name of the Item to a specific name.
     *
     * @param name The specified name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the ordered amount to a specified amount.
     *
     * @param onOrder The specified ordered amount.
     */
    public void setOnOrder(int onOrder) {
        this.onOrder = onOrder;
    }

    /**
     * Sets the price of the item to a specific price.
     *
     * @param price The specific price of the item.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the total stock of an Item to a specified amount.
     *
     * @param qtyInStore The specified stock of the Item.
     */
    public void setQtyInStore(int qtyInStore) {
        this.qtyInStore = qtyInStore;
    }

    /**
     * Return the formatter that will be used to format the price of the Item to the nearest 2 decimal places.
     *
     * @return The decimal formatter.
     */
    public DecimalFormat getDf() {
        return df;
    }

    /**
     * Sets the formatter to a specific decimal format
     *
     * @param df The specified decimal format.
     */
    public void setDf(DecimalFormat df) {
        this.df = df;
    }

    /**
     * Returns true if an order has been placed for more of the Item. Else, it returns false.
     *
     * @return Returns true or false if whether or not an order has been placed for more of the Item.
     */
    public boolean isOrder() {
        return order;
    }

    /**
     * Sets the boolean value to a specified boolean value.
     *
     * @param order The specified boolean value.
     */
    public void setOrder(boolean order) {
        this.order = order;
    }

    /**
     * Returns a String representation of the the Item object by including information such as the item code, name,
     * quantity in the the HashedGrocery, the price of the Item, the ordered amount of the Item, and the arrival day of
     * the Item.
     *
     * @return The String representation of the Item.
     */
    public String toString() {
        return String.format("%-15s%-20s%-20d%-15d%-15s%-15d%-15d", itemCode, name,
                qtyInStore, averageSalesPerDay, df.format(price), onOrder, arrivalDay);
    }
}
