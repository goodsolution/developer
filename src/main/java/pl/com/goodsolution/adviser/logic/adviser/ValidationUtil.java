package pl.com.goodsolution.adviser.logic.adviser;

public class ValidationUtil {

    public static void validateLength(String value, int maxLength, String fieldName) {
        if(value.equals("")) {
            throw new IllegalArgumentException("Field '" + fieldName + "' can't be empty!");
        } else if(value.length() > maxLength) {
            throw new IllegalArgumentException("Field '" + fieldName + "' is too long!");
        }
    }
}
