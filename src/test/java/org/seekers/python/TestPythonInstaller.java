package org.seekers.python;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class TestPythonInstaller {

    static PythonSettings settings = new PythonSettings();
    static PythonInstaller installer = new PythonInstaller(settings);

    @Test
    void download() throws IOException {
        installer.download();
    }

    @Test
    void workflow() throws IOException {
        installer.workflow();
    }

    @Test
    void clean() throws IOException {
        installer.clean();
    }
}
