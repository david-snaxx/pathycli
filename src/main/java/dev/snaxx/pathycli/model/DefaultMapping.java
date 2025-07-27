package dev.snaxx.pathycli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A Default Mapping object is used to map a user-specified alias/keyword to a program that should be used to open
 * a specific file type when the 'pathy open [alias]' command is used.
 */
public class DefaultMapping {

    @JsonProperty("programPath") private String programPath;
    @JsonProperty("fileType") private String fileType;

    /**
     * A Default Mapping object is used to map a user-specified alias/keyword to a program that should be used to open
     * a specific file type when the 'pathy open [alias]' command is used.
     * @param programPath The relative file path leading to the program.
     * @param fileType The type of file this program should be designated to open by default.
     */
    @JsonCreator
    public DefaultMapping(
            @JsonProperty("programPath") String programPath,
            @JsonProperty("fileType") String fileType
    ) {
        this.programPath = programPath;
        this.fileType = fileType;
    }


    public String getProgramPath() { return programPath; }
    public void setProgramPath(String programPath) { this.programPath = programPath; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }
}
