package org.seekers.python

import java.io.IOException
import java.util.*

class PythonSettings(private val properties: Properties = Properties()) {

    init {
        properties.load(javaClass.getResourceAsStream("settings.properties"))
    }

    private fun format(format: String): String {
        var build = format
        for ((key, value) in properties) {
            build = build.replace("{$key}", value.toString())
        }
        return build
    }

    @Throws(IOException::class)
    private fun readFile(file: String): String {
        javaClass.getResourceAsStream(file).use { stream ->
            if (stream == null) throw InternalError("File not found")
            return String(stream.readAllBytes())
        }
    }

    @get:Throws(IOException::class)
    var releasePage: String? = null
        get() {
            if (field == null) field = format(readFile("release-page.txt"))
            return field
        }
        private set

    @get:Throws(IOException::class)
    var execCommand: String? = null
        get() {
            if (field == null) field = format(readFile("exec-command.txt"))
            return field
        }
        private set

    operator fun get(key: String): String = properties.getProperty(key)
}
