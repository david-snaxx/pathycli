package dev.snaxx.pathycli.util;

public enum ExitCode {
    SUCCESS(0, "Success."),
    ALIAS_NOT_FOUND(1, "The given alias could not be found."),
    FILE_NOT_FOUND(2, "The given file path is invalid."),
    INVALID_ARGUMENT(3, "The given argument is invalid."),
    ALIAS_ALREADY_EXISTS(4, "The given alias already exists."),
    MISSING_PARAMETER(5, "A necessary parameter/argument is missing."),
    OS_NOT_SUPPORTED(6, "This desktop API is not supported by pathy."),
    PERMISSION_DENIED(7, "Insufficient privilege access. Cannot open file."),
    UNKNOWN(99, "Unknown error has occurred.");

    private final int code;
    private final String message;

    ExitCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() { return this.code; }

    public String message() { return this.message; }

    /**
     * Looks for a defined exit code matching the given integer.
     * @param code The integer that may or may not match an exit code.
     * @return An {@link ExitCode} matching the given integer OR {@code ExitCode.UNKNOWN} if no matches are found.
     */
    public static ExitCode getMatchingExitCode(int code) {
        for (ExitCode exitCode : ExitCode.values()) {
            if (exitCode.code == code) {
                return exitCode;
            }
        }
        return UNKNOWN;
    }
}
