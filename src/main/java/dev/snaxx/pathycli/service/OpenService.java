package dev.snaxx.pathycli.service;

import dev.snaxx.pathycli.model.AliasMapping;
import dev.snaxx.pathycli.util.ExitCode;
import dev.snaxx.pathycli.util.PathyUtils;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OpenService {

    public OpenService() {}

    /**
     * Attempts to open the file that the provided {@link AliasMapping} points towards.
     * @param alias The alias mapping matching the request.
     * @return An {@link ExitCode} for operation success/failure.
     */
    public int openFromAlias(AliasMapping alias) {
        // route the file based on its extension
        switch (alias.getFileType()) {
            case ".exe":
                openExecutableFromAlias(alias);
                break;
            default:
                return ExitCode.FILE_NOT_FOUND.code();
        }

        return ExitCode.SUCCESS.code();
    }

    /**
     * Launches the executable file specified by the provided {@link AliasMapping}.
     * @param alias The alias mapping object containing the relative file path.
     * @return An {@link ExitCode} for operation success/failure.
     */
    public int openExecutableFromAlias(AliasMapping alias) {
        // validate this is an executable
        if (!alias.getFileType().equals(".exe")) {
            return ExitCode.INVALID_ARGUMENT.code();
        }

        // try to open the executable
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", PathyUtils.resolvePlatformPath(alias).toString());
        try {
            pb.inheritIO().start();
        } catch (IOException ioException) {
            return ExitCode.FILE_NOT_FOUND.code();
        }

        return ExitCode.SUCCESS.code();
    }

    /**
     * Launches the executable file provided at the given absolute file path.
     * Use {@link #openExecutableFromAlias(AliasMapping)} if only a relative path is known.
     * @param request The absolute file path leading to the executable.
     * @return An {@link ExitCode} for operation success/failure.
     */
    public int openExecutableFromAbsolutePath(String request) {
        // validate this is an executable
        if (!request.toLowerCase().endsWith(".exe")) {
            return ExitCode.INVALID_ARGUMENT.code();
        }

        if (!Files.exists(Paths.get(request))) {
            return ExitCode.FILE_NOT_FOUND.code();
        }

        // try to open the executable
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", request);
        try {
            pb.inheritIO().start();
        } catch (IOException ioException) {
            return ExitCode.FILE_NOT_FOUND.code();
        }

        return ExitCode.SUCCESS.code();
    }

    public int openImageFromAlias(AliasMapping alias) {
        Path filePath = PathyUtils.resolvePlatformPath(alias);
        if (!Files.exists(filePath)) {
            return ExitCode.FILE_NOT_FOUND.code();
        }

        if (!Desktop.isDesktopSupported()) {
            return ExitCode.OS_NOT_SUPPORTED.code();
        }

        Desktop desktop = Desktop.getDesktop();

        if (!desktop.isSupported(Desktop.Action.OPEN)) {
            return ExitCode.OS_NOT_SUPPORTED.code();
        }

        try {
            desktop.open(filePath.toFile());
            return ExitCode.SUCCESS.code();
        } catch (IOException ioException) {
            return ExitCode.UNKNOWN.code();
        } catch (SecurityException securityException) {
            return ExitCode.PERMISSION_DENIED.code();
        }
    }

    public int openImageFromAbsolutePath(String request) {
        Path filePath = Paths.get(request);
        if (!Files.exists(filePath)) {
            return ExitCode.FILE_NOT_FOUND.code();
        }

        if (!Desktop.isDesktopSupported()) {
            return ExitCode.OS_NOT_SUPPORTED.code();
        }

        Desktop desktop = Desktop.getDesktop();

        if (!desktop.isSupported(Desktop.Action.OPEN)) {
            return ExitCode.OS_NOT_SUPPORTED.code();
        }

        try {
            desktop.open(filePath.toFile());
            return ExitCode.SUCCESS.code();
        } catch (IOException ioException) {
            return ExitCode.UNKNOWN.code();
        } catch (SecurityException securityException) {
            return ExitCode.PERMISSION_DENIED.code();
        }
    }
}
