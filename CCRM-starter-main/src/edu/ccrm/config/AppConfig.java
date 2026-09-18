package edu.ccrm.config;

import java.nio.file.*;
import java.io.IOException;
import java.util.Properties;

public final class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();
    private final Properties props = new Properties();
    private final Path dataDir;

    private AppConfig() {
        // Defaults
        this.dataDir = Paths.get("data");
        props.setProperty("data.dir", dataDir.toString());
        try {
            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to init data dir", e);
        }
    }

    public static AppConfig getInstance() {
        return INSTANCE;
    }

    public Path dataDir() {
        return this.dataDir;
    }

    public String get(String key, String def) {
        return props.getProperty(key, def);
    }
}
