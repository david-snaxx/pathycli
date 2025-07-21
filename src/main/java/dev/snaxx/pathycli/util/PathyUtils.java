package dev.snaxx.pathycli.util;

import dev.snaxx.pathycli.model.AliasMapping;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The PathyUtils class contains various utility classes that may/may not be moved to a more relevant class.
 */
public class PathyUtils {

    /**
     * @return The dot extension of the given file path. (ex. ".exe" not "exe")
     */
    public static String getFileExtension(String filePath) {
        return filePath.substring(filePath.lastIndexOf("."));
    }

    /**
     * Attempts to mutate the stored path into a full system path regardless of operating system and, for windows,
     * drive letter.
     * @param alias The alias to inspect the path of.
     * @return An absolute file path leading to the given aliased file.
     */
    public static Path resolvePlatformPath(AliasMapping alias) {
        Path relativePath = Paths.get(alias.getRelativePath());
        if (isWindows()) {
            // drive letter is not stored, so just loop through the possibilities
            for (File root : File.listRoots()) {
                Path withRoot = root.toPath().resolve(relativePath);
                if (Files.exists(withRoot)) {
                    return withRoot;
                }
            }
            return relativePath.toAbsolutePath(); // no drive letter found
        } else { // unix
            if (relativePath.isAbsolute()) {
                return relativePath;
            }
            return Paths.get(System.getProperty("user.home")).resolve(relativePath).normalize();
        }
    }

    /**
     * @return {@code TRUE} If this is a Windows computer. {@code FALSE} If not.
     */
    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().startsWith("windows");
    }
}


