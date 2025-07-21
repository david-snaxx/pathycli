package dev.snaxx.pathycli.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OpeningService {

    public OpeningService() {}

    public int openExecutableFromAbsolutePath(String request) {
        if (!request.toLowerCase().endsWith(".exe")) {
            return ExitCode.INVALID_ARGUMENT.code();
        }

        if (!Files.exists(Paths.get(request))) {
            return ExitCode.FILE_NOT_FOUND.code();
        }

        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", request);
        try {
            pb.redirectOutput(ProcessBuilder.Redirect.DISCARD)
                    .redirectError(ProcessBuilder.Redirect.DISCARD)
                    .inheritIO()
                    .start();
        } catch (IOException ioException) {
            return ExitCode.FILE_NOT_FOUND.code();
        }

        return ExitCode.SUCCESS.code();
    }
}
