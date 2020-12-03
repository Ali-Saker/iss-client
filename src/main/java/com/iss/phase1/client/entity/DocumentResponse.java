package com.iss.phase1.client.entity;

import com.iss.phase1.client.extra.AES;

import java.io.Serializable;

public class DocumentResponse implements Serializable {

    private static final long serialVersionUID = 7729685098267757690L;

    private String name;

    private String content;

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

    public DocumentResponse encryptName() {
        this.name = AES.encrypt(this.name);
        return this;
    }

    public DocumentResponse encryptContent() {
        this.content = AES.encrypt(this.content);
        return this;
    }

    public DocumentResponse decryptName() {
        this.name = AES.decrypt(this.name);
        return this;
    }

    public DocumentResponse decryptContent() {
        this.content = AES.decrypt(this.content);
        return this;
    }
}
