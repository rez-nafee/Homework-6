//Rezvan Nafee
//112936468
//Recitation 04

/**
 * This class represents a custom error class called InvalidJSOnFile. This error is caught when the HashedGrocery tries
 * to parse a file that is not in JSON format or it is not in the proper JSON format for the HashedGrocery to use.
 *
 * @author Rezvan Nafee
 * @ID 112936468
 * @Recitation Recitation 04
 */

public class InvalidJSONFile extends Exception {

    /**
     * Constructs the error message to be shown to the user when InvalidJSOnFile is thrown.
     *
     * @param error The error message.
     */
    public InvalidJSONFile(String error) {
        super(error);
    }
}
