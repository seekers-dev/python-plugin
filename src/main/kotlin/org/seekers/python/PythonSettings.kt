package org.seekers.python;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class PythonSettings {
    private final Properties properties = new Properties();

    public PythonSettings() {
        try {
            properties.load(getClass().getResourceAsStream("settings.properties"));
        } catch (IOException ex) {
            throw new InternalError("File not found");
        }
    }

    public String format(String format) {
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            format = format.replaceAll("\\{" + entry.getKey() + "}", entry.getValue().toString());
        }
        return format;
    }

    private String releasePage;
    private String execCommand;

    private String readFile(String file) throws IOException {
        try (InputStream stream = getClass().getResourceAsStream(file)) {
            if (stream == null) throw new InternalError("File not found");
            return new String(stream.readAllBytes());
        }
    }

    public String getReleasePage() throws IOException {
        if (releasePage == null)
            releasePage = format(readFile("release-page.txt"));
        return releasePage;
    }

    public String getExecCommand() throws IOException {
        if (execCommand == null)
            execCommand = format(readFile("exec-command.txt"));
        return execCommand;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
