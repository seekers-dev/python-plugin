package org.seekers.python

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.PosixFilePermissions

internal class TestPythonInstaller {

    @Test
    fun workflow() {
        Assertions.assertDoesNotThrow { installer.workflow() }
    }

    @Test
    fun download() {
        Assertions.assertDoesNotThrow { installer.download() }
    }

    @Test
    fun permissions() {
        Assertions.assertDoesNotThrow<Path> {
            Files.setPosixFilePermissions(File(settings["folder"], "client/run_client")
                .toPath(), PosixFilePermissions.fromString("rwxrwxrwx")) }
    }

    @Test
    fun clean() {
        Assertions.assertDoesNotThrow { installer.clean() }
    }

    @Test
    fun clear() {
        Assertions.assertDoesNotThrow { installer.clear() }
    }

    companion object {
        var settings: PythonSettings = PythonSettings()
        var installer: PythonInstaller = PythonInstaller(settings)
    }
}
