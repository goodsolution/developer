package pl.com.mike.developer.logic.courseplatform;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {

    public static String getClientIp(HttpServletRequest request) {


        if (request != null) {

            String xForwardedFor = request.getHeader("X-FORWARDED-FOR");

            if (xForwardedFor == null || xForwardedFor.equals("")) {
                 return request.getRemoteAddr();
            } else {
                return getFirstIp(xForwardedFor);
            }

        } else {
            return "";
        }

    }

    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    private static String getFirstIp (String xForwarderFor) {
        return xForwarderFor.replaceFirst(",.*", "");
    }
}
