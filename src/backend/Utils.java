package backend;

/**
 * Core utility functions.
 */
public class Utils {
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
