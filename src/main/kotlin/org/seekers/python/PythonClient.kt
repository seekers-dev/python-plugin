package org.seekers.python

import org.seekers.grpc.SeekersClient
import org.slf4j.LoggerFactory
import java.io.File

/**
 * The python client hosts a python client.
 */
class PythonClient: SeekersClient {
    companion object {
        private val logger = LoggerFactory.getLogger(SeekersClient::class.java)
    }

    private var process: Process? = null

    override fun close() {
        process?.destroy()
        logger.info("Client stopped")
    }

    override fun host(file: File) {
        val builder = ProcessBuilder(file.path)
        builder.redirectErrorStream(true)

        val log = File("$file.log")
        if (!log.exists() && !log.createNewFile()) { // Create log file
            logger.error("Could not create log file {}", log.absolutePath)
        }
        builder.redirectOutput(log)

        process = builder.start()
        logger.info("Client started")
    }
}
