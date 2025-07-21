package dev.snaxx.pathycli.service;

public enum ExitCode {
    SUCCESS(0, "Success."),
    ALIAS_NOT_FOUND(1, "The given alias could not be found."),
    FILE_NOT_FOUND(2, "The given file path is invalid."),
    INVALID_ARGUMENT(3, "The given argument is invalid."),
    UNKNOWN(99, "Unknown error has occurred.");

    private final int code;
    private final String message;

    ExitCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() { return this.code; }

    public String message() { return this.message; }
}
