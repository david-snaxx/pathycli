package dev.snaxx.pathycli.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigPaths {

    public static final Path ROOT_DIRECTORY =
            Paths.get(System.getProperty("user.home"), ".config", "pathycli");

    public static final Path ALIAS_JSON =
            ConfigPaths.ROOT_DIRECTORY.resolve("alias.json");

    public static final Path DEFAULTS_JSON =
            ConfigPaths.ROOT_DIRECTORY.resolve("defaults.json");
}
