package com.iss.phase1.client.entity;

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
}
