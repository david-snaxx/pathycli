package dev.snaxx.pathycli.service;

import dev.snaxx.pathycli.json.PersistenceReader;
import dev.snaxx.pathycli.model.AliasMapping;

public class AddService {

    public AddService() {}

    public int addNewAliasMapping(AliasMapping toAdd) {
        PersistenceReader persistenceReader = new PersistenceReader();

        int validationExitCode = persistenceReader.validatePathyFilesExist();
        if (validationExitCode != ExitCode.SUCCESS.code()) {
            return validationExitCode;
        }

        int addOpExitCode = persistenceReader.addNewAlias(toAdd);
        if (addOpExitCode != ExitCode.SUCCESS.code()) {
            return addOpExitCode;
        }

        return ExitCode.SUCCESS.code();
    }
}
