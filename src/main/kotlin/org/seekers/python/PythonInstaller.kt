package org.seekers.python

import java.io.*
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.PosixFilePermissions
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class PythonInstaller(private val settings: PythonSettings) {
    /**
     * Runs the default workflow. First checks for install folder. If the folder does not exist, it will download the
     * zip file from the release page, unpack it, give permissions to the execution file and delete all cached content.
     */
    @Throws(IOException::class)
    fun workflow() {
        if (!check()) {
            download()
            unpack()
            clear()
        }
    }

    /**
     * Check if install folder exists.
     */
    private fun check(): Boolean {
        return Files.exists(Path.of(settings["folder"]))
    }

    /**
     * Download zip file from release page.
     *
     * @throws IOException if it can not download from release page or can not write to output file.
     */
    @Throws(IOException::class)
    fun download() {
        BufferedInputStream(URL(settings.releasePage).openStream()).use { input ->
            FileOutputStream(settings["item"]).use { output ->
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while ((input.read(buffer, 0, 1024).also { bytesRead = it }) != -1) {
                    output.write(buffer, 0, bytesRead)
                }
            }
        }
    }

    /**
     * Unpacks a single zip entry.
     *
     * @param entry the zip entry
     * @param stream the zip input stream which is read from
     * @param directory the directory which is written
     */
    private fun unpackEntry(entry: ZipEntry, stream: ZipInputStream, directory: File) {
        val buffer = ByteArray(1024)
        val newFile = File(directory, entry.name)
        if (entry.isDirectory) {
            if (!newFile.isDirectory && !newFile.mkdirs()) {
                throw IOException("Failed to create directory $newFile")
            }
        } else {
            val parent = newFile.parentFile
            if (!parent.isDirectory && !parent.mkdirs()) {
                throw IOException("Failed to create directory $parent")
            }

            FileOutputStream(newFile).use { output ->
                var len: Int
                while ((stream.read(buffer).also { len = it }) > 0) {
                    output.write(buffer, 0, len)
                }
            }
        }
    }

    /**
     * Unpack downloaded zip file.
     */
    @Throws(IOException::class)
    fun unpack() {
        val fileZip = settings["item"]
        val directory = File(settings["folder"])

        ZipInputStream(FileInputStream(fileZip)).use { stream ->
            var entry = stream.nextEntry
            while (entry != null) {
                unpackEntry(entry, stream, directory) // Unpack single entry
                entry = stream.nextEntry
            }
            stream.closeEntry()
        }

        // Give target execution permission
        Files.setPosixFilePermissions(
            File(directory, "client/run_client").toPath(),
            PosixFilePermissions.fromString("rwxrwxrwx")
        )
    }

    /**
     * Clean installed files. Deletes the output folder and all files that are in this folder.
     */
    @Throws(IOException::class)
    fun clean(path: Path = Path.of(settings["folder"])) {
        if (Files.exists(path)) {
            if (Files.isDirectory(path)) Files.list(path).use { stream ->
                val iterator = stream.iterator()
                while (iterator.hasNext()) {
                    clean(iterator.next()) // Clean sub files
                }
            }
            Files.delete(path) // Delete file
        }
    }

    /**
     * Clear downloaded zip file. Deletes the cached file.
     */
    @Throws(IOException::class)
    fun clear() {
        Files.deleteIfExists(Path.of(settings["item"]))
    }
}
