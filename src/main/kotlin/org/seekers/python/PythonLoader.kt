package org.seekers.python

import org.seekers.grpc.SeekersClient
import org.seekers.plugin.LanguageLoader

class PythonLoader: LanguageLoader {
    private val patterns = listOf(".py", ".python")

    override fun recognizedPatterns(): List<String> = patterns

    override fun create(): SeekersClient {
        return PythonClient()
    }
}