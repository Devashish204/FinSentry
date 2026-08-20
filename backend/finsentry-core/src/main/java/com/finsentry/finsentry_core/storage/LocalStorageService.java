package com.finsentry.finsentry_core.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class LocalStorageService implements StorageService {

    private final Path storageLocation;

    public LocalStorageService(
            @Value("${storage.local.path:uploads}") String storagePath) {

        this.storageLocation = Paths.get(storagePath)
                .toAbsolutePath()
                .normalize();
    }

    @Override
    public String store(MultipartFile file) throws IOException {

        Files.createDirectories(storageLocation);

        String originalFilename = file.getOriginalFilename();

        String extension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(
                    originalFilename.lastIndexOf(".")
            );
        }

        String filename = UUID.randomUUID() + extension;

        Path target = storageLocation.resolve(filename);

        file.transferTo(target);

        return storageLocation.relativize(target).toString();
    }
}
