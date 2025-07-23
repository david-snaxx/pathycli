package dev.snaxx.pathycli.command;

import dev.snaxx.pathycli.json.PersistenceReader;
import dev.snaxx.pathycli.model.AliasMapping;
import dev.snaxx.pathycli.util.ExitCode;
import dev.snaxx.pathycli.service.OpenService;
import dev.snaxx.pathycli.util.PathyUtils;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.Callable;

@Command(name = "open",
        mixinStandardHelpOptions = true,
        description = "Open the requested file.")
public final class OpenCommand implements Callable<Integer> {

    @Parameters(index = "0",
            description = "The requested file's alias or absolute path.")
    private String request;

    @Option(names = { "-w", "--with" },
            description = "Bypass the default app and open this file with a specific app.")
    private boolean bypassDefaultApp;

    @Parameters(
            index = "1",
            description = "The path or alias leading to the app that should bypass the default opener.",
            arity = "0..1")
    private String bypassRequest;

    public OpenCommand() {}

    @Override
    public Integer call() throws Exception {
        if (!bypassDefaultApp) {
            return openNormally();
        }
        return openWithBypass();
    }

    /**
     * Attempts to open the requested file without bypassing the default app to be used.
     * @return An {@link ExitCode} for operation success/failure.
     */
    private int openNormally() {
        PersistenceReader persistenceReader = new PersistenceReader();
        OpenService openService = new OpenService();
        Optional<AliasMapping> potentialAlias = persistenceReader.getAliasByKey(this.request);

        // see if we were given a file path instead of an alias
        if (potentialAlias.isEmpty()) {
            return openNormallyFromPath(this.request);
        }

        // look for valid alias
        AliasMapping alias = potentialAlias.get();

        switch (alias.getFileType()) {
            case ".exe":
                return openService.openExecutableFromAlias(alias);
            case ".png", ".jpg":
                return openService.openImageFromAlias(alias);
            default:
                return ExitCode.UNKNOWN.code();
        }
    }

    /**
     * Attempts to see if instead of an alias, an absolute file path was given.
     * If one was, an attempt will be made to open the file.
     * @param path The potential file path provided.
     * @return An {@link ExitCode} for operation success/failure.
     */
    private int openNormallyFromPath(String path) {
        // either the paths is wrong or it wasn't a path
        if (!Files.exists(Paths.get(path))) {
            return ExitCode.INVALID_ARGUMENT.code();
        }

        OpenService openService = new OpenService();
        switch (PathyUtils.getFileExtension(path)) {
            case ".exe":
                return openService.openExecutableFromAbsolutePath(path);
            case ".png", ".jpg":
                return openService.openImageFromAbsolutePath(path);
            default:
                return ExitCode.INVALID_ARGUMENT.code();
        }
    }

    private int openWithBypass() {
        return ExitCode.UNKNOWN.code();
    }
}
