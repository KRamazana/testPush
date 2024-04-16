package ru.test.best.callback;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.callback.Context;
import org.flywaydb.core.api.callback.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
@Slf4j
public class FlywayAlterDropValidationCallback implements Callback {
    private static String ALTER_COLUMN = "alter column";
    private static String RENAME = "rename";
    private static String DROP = "drop";
    private static String DROP_CONSTRAINT = "drop constraint";
    @Value("${migration.validation.enabled}")
    private boolean enabledMigrationValidation;

    @Override
    public boolean supports(Event event, Context context) {
        return event == Event.BEFORE_EACH_MIGRATE;
    }

    @Override
    public boolean canHandleInTransaction(Event event, Context context) {
        return true;
    }

    @Override
    public void handle(Event event, Context context) {
        if (event.equals(Event.BEFORE_EACH_MIGRATE) && enabledMigrationValidation) {
//            log.info("Validating before migration");
            validate(context);
        }
    }

    @Override
    public String getCallbackName() {
        return FlywayAlterDropValidationCallback.class.getSimpleName();
    }


    private void validate(Context context){
        MigrationInfo currentMigration = context.getMigrationInfo();
        String script = String.format("db/migration/%s", currentMigration.getScript());

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getClassLoader().getResourceAsStream(script)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.toLowerCase();
                if (line.contains(ALTER_COLUMN)) {
                    illegalOperation(ALTER_COLUMN, currentMigration.getScript());
                }
                if (line.contains(RENAME)) {
                    illegalOperation(RENAME, currentMigration.getScript());
                }
                line = line.replaceAll(DROP_CONSTRAINT, "");
                if (line.contains(DROP)) {
                    illegalOperation(DROP, currentMigration.getScript());
                }
            }
        } catch (IOException e) {
            throw new FlywayException("Failed to read migration script: " + script, e);
        }
//        log.info("Migrating: {}", script);
    }

    private void illegalOperation(String operation, String script){
        throw new IllegalStateException(
                String.format("Illegal operations: [%s] are not allowed in migration script: [%s]",
                        operation, script));
    }
}