package br.com.senac.ouvidoria.service;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailCipher {

    private static final String VERSION_PREFIX = "v1:";
    private static final int KEY_LENGTH_BYTES = 32;
    private static final int NONCE_LENGTH_BYTES = 12;
    private static final int TAG_LENGTH_BITS = 128;
    private static final int TAG_LENGTH_BYTES = TAG_LENGTH_BITS / 8;

    private final SecretKeySpec key;
    private final SecureRandom secureRandom = new SecureRandom();

    public EmailCipher(@Value("${app.crypto.follow-up-email-key}") String encodedKey) {
        byte[] keyBytes;
        try {
            keyBytes = Base64.getDecoder().decode(encodedKey);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                    "The follow-up email encryption key must be Base64 encoded.", exception);
        }
        if (keyBytes.length != KEY_LENGTH_BYTES) {
            throw new IllegalArgumentException(
                    "The follow-up email encryption key must decode to 32 bytes.");
        }
        this.key = new SecretKeySpec(keyBytes, "AES");
    }

    public String encrypt(String plaintext) {
        byte[] nonce = new byte[NONCE_LENGTH_BYTES];
        secureRandom.nextBytes(nonce);

        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH_BITS, nonce));
            byte[] ciphertext = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            byte[] payload = ByteBuffer.allocate(nonce.length + ciphertext.length)
                    .put(nonce)
                    .put(ciphertext)
                    .array();
            return VERSION_PREFIX + Base64.getEncoder().encodeToString(payload);
        } catch (GeneralSecurityException exception) {
            throw new IllegalStateException("Could not encrypt the follow-up email.", exception);
        }
    }

    public String decrypt(String encryptedValue) {
        if (encryptedValue == null || !encryptedValue.startsWith(VERSION_PREFIX)) {
            throw new IllegalArgumentException("Unsupported encrypted email format.");
        }

        byte[] payload;
        try {
            payload = Base64.getDecoder().decode(encryptedValue.substring(VERSION_PREFIX.length()));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Invalid encrypted email value.", exception);
        }
        if (payload.length < NONCE_LENGTH_BYTES + TAG_LENGTH_BYTES) {
            throw new IllegalArgumentException("Invalid encrypted email value.");
        }

        byte[] nonce = Arrays.copyOfRange(payload, 0, NONCE_LENGTH_BYTES);
        byte[] ciphertext = Arrays.copyOfRange(payload, NONCE_LENGTH_BYTES, payload.length);
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH_BITS, nonce));
            return new String(cipher.doFinal(ciphertext), StandardCharsets.UTF_8);
        } catch (GeneralSecurityException exception) {
            throw new IllegalStateException(
                    "Could not decrypt the follow-up email. Check the encryption key.", exception);
        }
    }
}
