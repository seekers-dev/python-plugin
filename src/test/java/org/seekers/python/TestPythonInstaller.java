package org.seekers.python;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermissions;

class TestPythonInstaller {

    static PythonSettings settings = new PythonSettings();
    static PythonInstaller installer = new PythonInstaller(settings);

    @Test
    void download() {
        Assertions.assertDoesNotThrow(() -> installer.download());
    }

    @Test
    void workflow() {
        Assertions.assertDoesNotThrow(() ->  installer.workflow());
    }

    @Test
    void permissions() {
        Assertions.assertDoesNotThrow(() -> Files.setPosixFilePermissions(new File(settings.getProperty("folder"),
                "client/run_client").toPath(), PosixFilePermissions.fromString("rwxrwxrwx")));
    }

    @Test
    void clean() {
        Assertions.assertDoesNotThrow(() -> installer.clean());
    }

    @Test
    void clear() {
        Assertions.assertDoesNotThrow(() -> installer.clear());
    }
}
