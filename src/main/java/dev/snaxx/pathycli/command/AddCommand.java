package dev.snaxx.pathycli.command;

import dev.snaxx.pathycli.model.AliasMapping;
import dev.snaxx.pathycli.service.AddService;
import dev.snaxx.pathycli.util.PathyUtils;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "add",
        mixinStandardHelpOptions = true,
        description = "Add a new alias to point to a file.")
public final class AddCommand implements Callable<Integer> {

    @Parameters(index = "0",
            description = "The unique alias keyword to use.")
    private String alias;

    @Parameters(index = "1",
            description = "The file path to use when using this alias.")
    private String filePath;

    public AddCommand() {}

    @Override
    public Integer call() throws Exception {
        AddService addService = new AddService();
        AliasMapping toAdd =
                new AliasMapping(this.alias, this.filePath, PathyUtils.getFileExtension(this.filePath));
        return addService.addNewAliasMapping(toAdd);
    }
}
