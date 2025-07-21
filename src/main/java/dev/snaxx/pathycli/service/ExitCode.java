package dev.snaxx.pathycli.service;

public enum ExitCode {
    SUCCESS(0, "Success."),
    ALIAS_NOT_FOUND(1, "The given alias could not be found."),
    FILE_NOT_FOUND(2, "The given file path is invalid.");

    private final int code;
    private final String message;

    ExitCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() { return this.code; }

    public String message() { return this.message; }
}
