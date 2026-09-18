package edu.ccrm.io;

import edu.ccrm.config.AppConfig;
import edu.ccrm.util.FileUtils;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupService {
    private final AppConfig cfg = AppConfig.getInstance();

    public Path backupExports() throws IOException {
        Path exportDir = cfg.dataDir().resolve("export");
        if (!Files.exists(exportDir)) {
            throw new IOException("Nothing to backup; export first.");
        }
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        Path backupDir = cfg.dataDir().resolve("backup").resolve(ts);
        Files.createDirectories(backupDir);
        // Copy files recursively
        try (var paths = Files.walk(exportDir)) {
            paths.forEach(p -> {
                try {
                    Path target = backupDir.resolve(exportDir.relativize(p).toString());
                    if (Files.isDirectory(p)) {
                        Files.createDirectories(target);
                    } else {
                        Files.copy(p, target, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) { throw new RuntimeException(e); }
            });
        }
        long size = FileUtils.recursiveSize(backupDir);
        System.out.println("Backup created at " + backupDir + " (size=" + size + " bytes)");
        return backupDir;
    }
}
