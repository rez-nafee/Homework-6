//Rezvan Nafee
//112936468
//Recitation 04

/**
 * This class represents a custom error class called DuplicateItem. This error is caught when an Item that
 * is being added to the HashedGrocery already exists by checking for the item code of the Item.
 *
 * @author Rezvan Nafee
 * @ID 112936468
 * @Recitation Recitation 04
 */

public class DuplicateItem extends Exception {

    /**
     * Constructs the error message to be shown to the user when DuplicateItem is thrown.
     *
     * @param error The error message.
     */
    public DuplicateItem(String error) {
        super(error);
    }
}
