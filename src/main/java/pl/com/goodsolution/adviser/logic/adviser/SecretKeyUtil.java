package pl.com.goodsolution.adviser.logic.adviser;

import java.util.UUID;

public class SecretKeyUtil {

    public static String generate() {
        String firstUuid = UUID.randomUUID().toString();
        String secondUuid = UUID.randomUUID().toString();
        String dateInMillis = "-" + System.currentTimeMillis() + "-";
        return firstUuid + dateInMillis + secondUuid;
    }
}
