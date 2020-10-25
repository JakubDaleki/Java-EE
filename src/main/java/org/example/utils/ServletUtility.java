package org.example.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Additional utility methods for servlets. Instead of defining them in every servlet separate utility class is used.
 */
public class ServletUtility {

    public static String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path == null ? "" : path;
        return path;
    }

    public static boolean hasUrlArguments(HttpServletRequest req) {
        String arguments = ServletUtility.parseRequestPath(req).replaceAll("/", "");
        if (arguments.equals(""))
            return false;

        return true;
    }

}
