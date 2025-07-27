package dev.snaxx.pathycli.command;


import dev.snaxx.pathycli.util.ExitCode;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ArgGroup;

import java.util.concurrent.Callable;

@Command(name = "default",
        description = "Change default apps used with the open command.",
        mixinStandardHelpOptions = true)
public final class DefaultCommand implements Callable<Integer> {

    @ArgGroup(heading = "File types that can have defaults specified.",
            multiplicity = "0..1")
    private FileTypeOptions fileTypeOptions;
    public static class FileTypeOptions {
        @Option(names = { "-img", "--image" },
                description = "Change the default app to open images.")
        private boolean image;

        @Option(names = { "-txt", "--text" },
                description = "Change the default app to open text documents.")
        private boolean text;
    }

    @Parameters(index = "0",
            description = "The file path or alias leading to the new default program.")
    private String program;

    @Override
    public Integer call() throws Exception {
        return ExitCode.SUCCESS.code();
    }
}
