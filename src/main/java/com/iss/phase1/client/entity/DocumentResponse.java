package com.iss.phase1.client.entity;

import com.iss.phase1.client.extra.DigitalSignature;

import java.io.Serializable;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

public class DocumentResponse implements Serializable {

    private static final long serialVersionUID = 7729685098267757690L;

    private String name;

    private String content;

    private X509Certificate serverCertificate;

    private byte [] signedName;

    private byte [] signedContent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public X509Certificate getServerCertificate() {
        return serverCertificate;
    }

    public void setServerCertificate(X509Certificate serverCertificate) {
        this.serverCertificate = serverCertificate;
    }

    public DocumentResponse signName() {
        this.signedName = DigitalSignature.sign(this.name);
        return this;
    }

    public DocumentResponse signContent() {
        this.signedContent = DigitalSignature.sign(this.content);
        return this;
    }

    public DocumentResponse verifyName(PublicKey publicKey) {
        boolean isCorrect = DigitalSignature.verify(this.name, this.signedName, publicKey);
        if(!isCorrect) {
            throw new RuntimeException("Unable to verify document response!");
        }
        return this;
    }

    public DocumentResponse verifyContent(PublicKey publicKey) {
        boolean isCorrect = DigitalSignature.verify(this.content, signedContent, publicKey);
        if(!isCorrect) {
            throw new RuntimeException("Unable to verify document response!");
        }
        return this;
    }
}
