package com.iss.phase1.client.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

public class BaseEntity implements Serializable {

    private Long id;

    private Date createdDate;

    private Date lastModifiedDate;

    private String uuid;

    public Long getId() {
        return id;
    }

    public String getUuid() {
        return this.uuid;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

}
