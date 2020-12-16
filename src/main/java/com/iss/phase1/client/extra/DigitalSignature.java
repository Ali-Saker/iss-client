package com.iss.phase1.client.extra;

import java.security.*;

public class DigitalSignature {


    private static KeyPair keyPair;

    static {
        try {
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            keyPair = keyGen.generateKeyPair();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static byte [] sign(String text) {
        byte [] signedText = null;
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(getPrivateKey());
            signature.update(text.getBytes());
            signedText = signature.sign();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return signedText;
    }


    public static boolean verify(String originalText, byte [] signedText, PublicKey publicKey) {
        boolean isCorrect = false;
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            signature.update(originalText.getBytes());
            isCorrect = signature.verify(signedText);
        } catch (Exception ex) {
            ex.printStackTrace();;
        }

        return isCorrect;
    }



    public static PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public static PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }
}
