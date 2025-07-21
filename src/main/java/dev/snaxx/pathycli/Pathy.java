package dev.snaxx.pathycli;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "pathy",
        mixinStandardHelpOptions = true,
        version = "pathy 1.0")
public class Pathy implements Callable<Integer> {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Pathy())
                .execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
