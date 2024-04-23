/*
 * Copyright (C) 2024  Seekers Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.seekers.python

import org.seekers.grpc.SeekersClient
import org.slf4j.LoggerFactory
import java.io.File

/**
 * The python client hosts a python client.
 */
class PythonClient: SeekersClient {
    companion object {
        private val logger = LoggerFactory.getLogger(PythonClient::class.java)

        // Class must be loaded after extension setup, otherwise it will throw an exception
        private val execCmd = PythonPlugin.config!!.fetch("exec-cmd")
    }

    private var process: Process? = null

    override fun close() {
        process?.destroy()
        logger.info("Client stopped")
    }

    override fun host(file: File) {
        val cmd = execCmd.replace("{file}", file.path).replace("\n", " ")
            .split(" ").filter { it.isNotBlank() }
        val builder = ProcessBuilder(cmd)
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
