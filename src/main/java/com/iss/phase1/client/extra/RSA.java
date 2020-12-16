package com.iss.phase1.client.extra;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class RSA {

    private static KeyPair keyPair;
    private final static String RSA = "RSA";

    public static void init() {
        try {
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(RSA);
            keyGen.initialize(1024);
            keyPair = keyGen.generateKeyPair();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public static PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }

    public static byte[] encrypt(byte[] text, PublicKey key) {
        byte[] cipherText = null;
        try {
            final Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText = cipher.doFinal(text);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cipherText;
    }

    public static byte[] decrypt(byte[] text) {
        byte[] decryptedText = null;
        try {
            final Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
            decryptedText = cipher.doFinal(text);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return decryptedText;
    }
}
