package org.seekers.python;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PythonInstaller {

    private final PythonSettings settings;

    public PythonInstaller(PythonSettings settings) {
        this.settings = settings;
    }

    public void workflow() throws IOException {
        if (!check()) {
            download();
            unpack();
            clear();
        }
    }

    public boolean check() {
        return Files.exists(Path.of(settings.getProperty("folder")));
    }

    /**
     * Download item from release page.
     *
     * @throws IOException if it can not download from release page or can not write to output file.
     */
    public void download() throws IOException {
        System.out.print('[');
        try (BufferedInputStream input = new BufferedInputStream(new URL(settings.getReleasePage()).openStream());
             FileOutputStream output = new FileOutputStream(settings.getProperty("item"))) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer, 0, 1024)) != -1) {
                output.write(buffer, 0, bytesRead);
                System.out.print('+');
            }
        }
        System.out.println(']');
    }

    /**
     * Unpack downloaded file
     */
    public void unpack() throws IOException {
        String fileZip = settings.getProperty("item");
        File directory = new File(settings.getProperty("folder"));

        byte[] buffer = new byte[1024];
        try (ZipInputStream stream = new ZipInputStream(new FileInputStream(fileZip))) {
            ZipEntry entry = stream.getNextEntry();
            while (entry != null) {
                File newFile = new File(directory, entry.getName());
                if (entry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }

                    try (FileOutputStream output = new FileOutputStream(newFile)) {
                        int len;
                        while ((len = stream.read(buffer)) > 0) {
                            output.write(buffer, 0, len);
                        }
                    }
                }
                entry = stream.getNextEntry();
            }
            stream.closeEntry();
        }

        // Give target execution permissions
        Files.setPosixFilePermissions(new File(directory, "client/run_client").toPath(),
                PosixFilePermissions.fromString("rwxrwxrwx"));
    }

    private static void clean(Path folder) throws IOException {
        try (Stream<Path> stream = Files.list(folder)) {
            var iterator = stream.iterator();
            while (iterator.hasNext()) {
                var item = iterator.next();
                if (Files.isDirectory(item)) {
                    clean(item);
                } else {
                    Files.delete(item);
                }
            }
        }
        Files.delete(folder);
    }

    /**
     * Clean installed files.
     */
    public void clean() throws IOException {
        Path path = Path.of(settings.getProperty("folder"));
        if (Files.exists(path) && Files.isDirectory(path)) {
            clean(path);
        }
    }

    /**
     * Clear downloaded zip file.
     */
    public void clear() throws IOException {
        Files.deleteIfExists(Path.of(settings.getProperty("item")));
    }
}
