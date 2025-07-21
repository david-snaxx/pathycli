package dev.snaxx.pathycli.command;

import dev.snaxx.pathycli.service.OpenService;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "open",
        mixinStandardHelpOptions = true,
        description = "Open the requested file.")
public final class OpenCommand implements Callable<Integer> {

    @Parameters(index = "0",
            description = "The requested file's alias or absolute path.")
    private String request;

    public OpenCommand() {}

    @Override
    public Integer call() throws Exception {
        OpenService openService = new OpenService();
        return openService.openExecutableFromAbsolutePath(this.request);
    }
}
