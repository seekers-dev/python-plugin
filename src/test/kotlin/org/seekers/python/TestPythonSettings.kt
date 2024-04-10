package org.seekers.python

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TestPythonSettings {

    @Test
    fun release() {
        val version = settings["version"]
        val item = settings["item"]
        Assertions.assertEquals("https://github.com/seekers-dev/seekers-py/releases/download/$version/$item",
            settings.releasePage)
    }

    companion object {
        val settings = PythonSettings()
    }
}