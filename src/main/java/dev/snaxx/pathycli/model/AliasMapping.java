package dev.snaxx.pathycli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An Alias Mapping object is used to map a user-specified alias/keyword to a file on their system.
 */
public class AliasMapping {

    @JsonProperty("alias") private final String alias;
    @JsonProperty("relativePath") private String relativePath;
    @JsonProperty("fileType") private String fileType;

    /**
     * An Alias Mapping object is used to map a user-specified alias/keyword to a file on their system.
     * @param alias The chosen alias/keyword.
     * @param relativePath The relative file path.
     * @param fileType The type of file (i.e. exe, png, txt...)
     */
    @JsonCreator
    public AliasMapping(
            @JsonProperty("alias") String alias,
            @JsonProperty("relativePath") String relativePath,
            @JsonProperty("fileType") String fileType
    ) {
        this.alias = alias;
        this.relativePath = relativePath;
        this.fileType = fileType;
    }


    public String getAlias() { return alias; }

    public String getRelativePath() { return relativePath; }
    public void setRelativePath(String relativePath) { this.relativePath = relativePath; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }
}
