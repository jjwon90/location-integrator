package com.jwjung.location;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
public class EncryptTest {
    @Autowired
    StringEncryptor encryptor;

    @Test
    void encryptTest() {
    }
}
