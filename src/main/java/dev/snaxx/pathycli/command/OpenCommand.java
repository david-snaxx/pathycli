package dev.snaxx.pathycli.command;

import dev.snaxx.pathycli.json.PersistenceReader;
import dev.snaxx.pathycli.model.AliasMapping;
import dev.snaxx.pathycli.service.ExitCode;
import dev.snaxx.pathycli.service.OpenService;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

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

    private int openNormally() {
        PersistenceReader persistenceReader = new PersistenceReader();
        OpenService openService = new OpenService();
        Optional<AliasMapping> potentialAlias = persistenceReader.getAliasByKey(this.request);

        // see if we were given a file path instead of an alias
        // todo: handle when a path is given instead of an alias
        if (potentialAlias.isEmpty()) {
            return ExitCode.UNKNOWN.code();
        }

        // look for valid alias
        AliasMapping alias = potentialAlias.get();
        return openService.openFromAlias(alias);
    }

    private int openWithBypass() {
        return ExitCode.UNKNOWN.code();
    }
}
