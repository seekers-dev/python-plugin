package org.seekers.python

import org.junit.jupiter.api.Test
import java.io.File

class TestPythonClient {

    @Test
    fun host() {
        val client = PythonClient()
        client.host(File(javaClass.getResource("ai-decide.py")!!.file))
    }
}