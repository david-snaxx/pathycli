package dev.snaxx.pathycli.command;

import dev.snaxx.pathycli.json.PersistenceReader;
import dev.snaxx.pathycli.model.AliasMapping;
import dev.snaxx.pathycli.model.DefaultMapping;
import dev.snaxx.pathycli.util.ExitCode;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ArgGroup;

import java.util.Optional;
import java.util.concurrent.Callable;

@Command(name = "config",
        description = "Configure stored aliases and behaviors of pathy.",
        mixinStandardHelpOptions = true)
public final class ConfigCommand implements Callable<Integer> {

    @ArgGroup(heading = "Accepted options for what config action to take.",
        multiplicity = "0..1")
    private ConfigOptions options;
    public static class ConfigOptions {
        @Option(names = { "-g", "--get" },
                description = "Get information on a single alias.")
        private boolean get;

        @Option(names = { "-ga", "--getall" },
                description = "Get info on all stored aliases.")
        private boolean getAll;
    }

    @ArgGroup(heading = "Specify which config file to work with.",
        multiplicity = "0..1")
    private ConfigOptionsFile optionsFile;
    public static class ConfigOptionsFile {
        @Option(names = { "-a", "--alias" },
                description = "Specify interest in currently stored file aliases.")
        private boolean fileAliases;

        @Option(names = { "-d", "--default" },
                description = "Specift interest in currently stored default app aliases.")
        private boolean defaultApps;
    }

    @Parameters(index = "0",
            description = "The arguments for the chosen operation; for example the aliases to look up with --get")
    private String[] args;

    @Override
    public Integer call() {
        if (options.get) {
            if (optionsFile.fileAliases) {
                return getAliasInfo();
            } else if (optionsFile.defaultApps) {
                return getDefaultInfo();
            }
            // look in both files if they were not specific
            return getAliasAndDefaultInfo();
        } else if (options.getAll) {
            if (optionsFile.fileAliases) {
                return getAllAliasInfo();
            } else if (optionsFile.defaultApps) {
                return getAllDefaultInfo();
            }
            // look in both files if they were not specific
            return getAllInfo();
        }

        return ExitCode.SUCCESS.code();
    }

    private int getAliasInfo() {
        PersistenceReader persistenceReader = new PersistenceReader();
        for (String arg : args) {
            Optional<AliasMapping> optional = persistenceReader.getAliasByKey(arg);
            if (optional.isEmpty()) {
                continue;
            }
            AliasMapping aliasMapping = optional.get();
            printInfo(aliasMapping);
        }
        return ExitCode.SUCCESS.code();
    }

    private int getAllAliasInfo() {
        return ExitCode.SUCCESS.code();
    }

    private int getDefaultInfo() {
        return ExitCode.SUCCESS.code();
    }

    private int getAllDefaultInfo() {
        return ExitCode.SUCCESS.code();
    }

    private int getAllInfo() {
        return ExitCode.SUCCESS.code();
    }

    private int getAliasAndDefaultInfo() {
        return ExitCode.SUCCESS.code();
    }

    private void printInfo(AliasMapping aliasMapping) {

    }

    private void printInfo(DefaultMapping defaultMapping) {

    }
}
