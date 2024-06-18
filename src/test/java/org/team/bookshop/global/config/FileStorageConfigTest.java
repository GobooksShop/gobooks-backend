package org.team.bookshop.global.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local")
class FileStorageConfigTest {

    @Autowired
    private FileStorageConfig fileStorageConfig;

    @Test
    public void testFileStorageConfig() {
        System.out.println(fileStorageConfig);
    }
}