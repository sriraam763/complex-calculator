/**
 * Contains a basic Utils class with global variables and generic functions.
 */

package backend;

import java.awt.event.KeyEvent;

/**
 * Core utility functions.
 */
public class Utils {
    /**
	 * Array of allowed keys that can be input.
	 */
	public final static Character ALLOWED_KEYS[] = {
        '%', '+', '-', '/', 'x', '*', '(', ')', '^',
        '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
        KeyEvent.VK_BACK_SPACE
    };

    /**
     * Check if a value is in an array.

     */
    public static <T> boolean valInArray(T val, T[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (val == arr[i]) {
                return true;
            }
        }

        return false;
    }
}
