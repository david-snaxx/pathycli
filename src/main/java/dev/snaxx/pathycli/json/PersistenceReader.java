package dev.snaxx.pathycli.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.snaxx.pathycli.model.AliasMapping;
import dev.snaxx.pathycli.util.ExitCode;
import dev.snaxx.pathycli.util.ConfigPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class PersistenceReader {

    /**
     * Checks to see if all necessary files exist for pathy including:
     * - The config directory @ ~/.config/pathycli
     * - The aliases.json data file
     * - The defaults.json data file
     * Any missing files will be created in their default state.
     * @return An {@link ExitCode} for operation success/failure.
     */
    public int validatePathyFilesExist() {
        // ~/.config/pathycli
        if (!Files.exists(ConfigPaths.ROOT_DIRECTORY)) {
            try {
                Files.createDirectories(ConfigPaths.ROOT_DIRECTORY);
            } catch (IOException ioException) {
                return ExitCode.UNKNOWN.code();
            }
        }

        // ~/.config/pathycli/aliases.json
        if (!Files.exists(ConfigPaths.ALIAS_JSON)) {
            try {
                Files.writeString(ConfigPaths.ALIAS_JSON, "{}", StandardOpenOption.CREATE_NEW);
            } catch (IOException ioException) {
                return ExitCode.UNKNOWN.code();
            }
        }

        // ~/.config/pathycli/defaults.json
        if (!Files.exists(ConfigPaths.DEFAULTS_JSON)) {
            try {
                Files.writeString(ConfigPaths.DEFAULTS_JSON, "{}", StandardOpenOption.CREATE_NEW);
            } catch (IOException ioException) {
                return ExitCode.UNKNOWN.code();
            }
        }

        return ExitCode.SUCCESS.code();
    }


    /**
     * Attempts to find a matching entry for the requested alias in the aliases.json file.
     * @param aliasKey The requested alias entry.
     * @return An optional containing a {@link AliasMapping} object matching the request.
     *         An empty optional in this instance signals that no matching alias exists.
     */
    public Optional<AliasMapping> getAliasByKey(String aliasKey) {
        ObjectMapper objectMapper = new ObjectMapper();

        // parse the json file for the key
        JsonNode rootNode;
        try {
            rootNode = objectMapper.readTree(ConfigPaths.ALIAS_JSON.toFile());
        } catch (IOException ioException) {
            return Optional.empty();
        }
        JsonNode aliasNode = rootNode.get(aliasKey);

        if (aliasNode == null || aliasNode.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(objectMapper.convertValue(aliasNode, AliasMapping.class));
    }

    /**
     * Attempts to add a new {@link AliasMapping} entry to the aliases.json file.
     * If a matching alias is already present, a new one is not added.
     * @param toAdd The new alias to add.
     * @return An {@link ExitCode} for operation success/failure.
     */
    public int addNewAlias(AliasMapping toAdd) {
        if (getAliasByKey(toAdd.getAlias()).isPresent()) {
            return ExitCode.ALIAS_ALREADY_EXISTS.code();
        }

        // parse the json file
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode;
        try {
            rootNode = (ObjectNode) objectMapper.readTree(ConfigPaths.ALIAS_JSON.toFile());
        } catch (IOException ioException) {
            return ExitCode.UNKNOWN.code();
        }

        // append the new entry
        JsonNode aliasNode = objectMapper.valueToTree(toAdd);
        rootNode.set(toAdd.getAlias(), aliasNode);
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(ConfigPaths.ALIAS_JSON.toFile(), rootNode);
        } catch (IOException ioException) {
            return ExitCode.UNKNOWN.code();
        }

        return ExitCode.SUCCESS.code();
    }

    /**
     * Retrieves all aliases currently stored in the aliases.json file.
     * The string key of the map represents the alias chosen by the user.
     * @return A Map of all current user specified aliases.
     */
    public Map<String, AliasMapping> getAllAliases() {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, AliasMapping> aliases = null;

        try {
            aliases = objectMapper.readValue(ConfigPaths.ALIAS_JSON.toFile(), new  TypeReference<Map<String, AliasMapping>>() {});
        } catch (IOException ioException) {
            System.err.println("IO error retrieving all aliases, check that aliases.json exists.");
        }

        if (aliases == null) {
            return Collections.emptyMap();
        }
        return aliases;
    }
}
