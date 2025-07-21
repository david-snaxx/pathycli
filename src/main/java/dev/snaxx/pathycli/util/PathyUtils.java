package dev.snaxx.pathycli.util;

public class PathyUtils {

    public static String getFileExtension(String filePath) {
        return filePath.substring(filePath.lastIndexOf("."));
    }
}
