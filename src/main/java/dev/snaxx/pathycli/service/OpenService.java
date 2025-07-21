package dev.snaxx.pathycli.service;

import dev.snaxx.pathycli.json.PersistenceReader;
import dev.snaxx.pathycli.model.AliasMapping;
import dev.snaxx.pathycli.util.PathyUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class OpenService {

    public OpenService() {}

    public int openExecutable(String request) {
        PersistenceReader persistenceReader = new PersistenceReader();
        Optional<AliasMapping> potentialAlias = persistenceReader.getAliasByKey(request);

        // see if we were given a file path instead of an alias
        if (potentialAlias.isEmpty()) {
            return openExecutableFromAbsolutePath(request);
        }

        // look for valid alias
        AliasMapping alias = potentialAlias.get();
        return openExecutableFromAliasMapping(alias);
    }

    /**
     * Launches the executable file specified by the provided {@link AliasMapping}.
     * @param alias The alias mapping object containing the relative file path.
     * @return An {@link ExitCode} for operation success/failure.
     */
    public int openExecutableFromAliasMapping(AliasMapping alias) {
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
     * Use {@link #openExecutableFromAliasMapping(AliasMapping)} if only a relative path is known.
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
}
