package dev.snaxx.pathycli;

import dev.snaxx.pathycli.command.AddCommand;
import dev.snaxx.pathycli.command.ConfigCommand;
import dev.snaxx.pathycli.command.DefaultCommand;
import dev.snaxx.pathycli.command.OpenCommand;
import dev.snaxx.pathycli.util.ExitCode;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "pathy",
        mixinStandardHelpOptions = true,
        version = "pathy 1.0")
public class Pathy implements Callable<Integer> {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Pathy())
                .addSubcommand(new OpenCommand())
                .addSubcommand(new AddCommand())
                .addSubcommand(new ConfigCommand())
                .addSubcommand(new DefaultCommand())
                .execute(args);
        System.out.println(exitCode);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        return ExitCode.SUCCESS.code();
    }
}
