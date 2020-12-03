package com.iss.phase1.client.entity;

import com.iss.phase1.client.extra.AES;
import com.iss.phase1.client.extra.ActionType;

import java.io.Serializable;

public class DocumentRequest implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;

    private String documentName;

    private ActionType actionType;

    private String updatedContent;

    public DocumentRequest() {
    }

    public DocumentRequest(String documentName, ActionType actionType, String updatedContent) {
        this.documentName = documentName;
        this.actionType = actionType;
        this.updatedContent = updatedContent;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public String getUpdatedContent() {
        return updatedContent;
    }

    public void setUpdatedContent(String updatedContent) {
        this.updatedContent = updatedContent;
    }

    public DocumentRequest encryptName() {
        this.documentName = AES.encrypt(this.documentName);
        return this;
    }

    public DocumentRequest encryptContent() {
        this.updatedContent = AES.encrypt(this.updatedContent);
        return this;
    }

    public DocumentRequest decryptName() {
        this.documentName = AES.decrypt(this.documentName);
        return this;
    }

    public DocumentRequest decryptContent() {
        this.updatedContent = AES.decrypt(this.updatedContent);
        return this;
    }
}
