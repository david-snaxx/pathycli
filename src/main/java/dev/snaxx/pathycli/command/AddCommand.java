package dev.snaxx.pathycli.command;

import dev.snaxx.pathycli.model.AliasMapping;
import dev.snaxx.pathycli.service.AddService;
import dev.snaxx.pathycli.util.PathyUtils;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.nio.file.Path;
import java.nio.file.Paths;
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
                new AliasMapping(this.alias,
                        removeWindowsDriveLetter(this.filePath),
                        PathyUtils.getFileExtension(this.filePath));
        return addService.addNewAliasMapping(toAdd);
    }

    public String removeWindowsDriveLetter(String absolutePath) {
        Path path = Paths.get(absolutePath);

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            String pathStr = path.toString();
            // Match something like "C:\" at the beginning
            if (pathStr.matches("^[a-zA-Z]:\\\\.*")) {
                return pathStr.substring(2); // Remove "C:"
            }
        }
        // If not Windows or no drive letter, return as-is
        return path.toString();
    }
}
