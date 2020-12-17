package com.iss.phase1.client.extra;

import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V1CertificateGenerator;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Date;

public class CertificateAuthority {

    private static PublicKey getPublicKey() {
        PublicKey publicKey = null;
        try {
            File publicKeyFile = Paths.get(System.getProperty("java.io.tmpdir"),
                    "ISSPublicKey").toFile();
            publicKey = (PublicKey) new ObjectInputStream(new FileInputStream(publicKeyFile)).readObject();
        } catch (Exception ex) {
            throw new RuntimeException("Unable to load the certificate authority's public key!", ex);
        }
        return publicKey;
    }

    private static PrivateKey getPrivateKey() {
        PrivateKey privateKey = null;
        try {
            File privateKeyFile = Paths.get(System.getProperty("java.io.tmpdir"),
                    "ISSPrivateKey").toFile();
            privateKey = (PrivateKey) new ObjectInputStream(new FileInputStream(privateKeyFile)).readObject();
        } catch (Exception ex) {
            throw new RuntimeException("Unable to load the certificate authority's private key!", ex);
        }
        return privateKey;
    }


    public static X509Certificate generateV1Certificate(PublicKey PK, String additionalData)
            throws InvalidKeyException, SignatureException {
        Security.addProvider(new BouncyCastleProvider());
        X509V1CertificateGenerator certGen = new X509V1CertificateGenerator();

        certGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
        certGen.setIssuerDN(new X509Principal("CN=CLIENT"));
        certGen.setNotBefore(new Date(System.currentTimeMillis() - 500000));
        certGen.setNotAfter(new Date(System.currentTimeMillis() + 500000));
        certGen.setSubjectDN(new X509Name("CN=" + additionalData));
        certGen.setPublicKey(PK);

        certGen.setSignatureAlgorithm("SHA256WithRSAEncryption");

        return certGen.generateX509Certificate(getPrivateKey());
    }

    public static boolean verifyCertificate(X509Certificate certificate) {
        try {
            certificate.verify(getPublicKey());
            certificate.checkValidity();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
