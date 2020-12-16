package com.iss.phase1.client.entity;

import com.iss.phase1.client.extra.ActionType;
import com.iss.phase1.client.extra.DigitalSignature;

import java.io.Serializable;
import java.security.PublicKey;

public class DocumentRequest implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;

    private String documentName;

    private ActionType actionType;

    private String updatedContent;

    private byte [] signedDocumentName;

    private byte [] signedUpdatedContent;

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

    public DocumentRequest signName() {
        this.signedDocumentName = DigitalSignature.sign(this.documentName);
        return this;
    }

    public DocumentRequest signContent() {
        this.signedUpdatedContent = DigitalSignature.sign(this.updatedContent);
        return this;
    }

    public DocumentRequest verifyName(PublicKey publicKey) {
        boolean isCorrect = DigitalSignature.verify(this.documentName, signedDocumentName, publicKey);
        if(!isCorrect) {
            throw new RuntimeException("Unable to verify document request!");
        }
        return this;
    }

    public DocumentRequest verifyContent(PublicKey publicKey) {
        boolean isCorrect = DigitalSignature.verify(this.updatedContent, signedUpdatedContent, publicKey);
        if(!isCorrect) {
            throw new RuntimeException("Unable to verify document request!");
        }
        return this;
    }
}
