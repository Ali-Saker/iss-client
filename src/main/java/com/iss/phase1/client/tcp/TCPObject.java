package com.iss.phase1.client.tcp;

import java.io.Serializable;

public class TCPObject implements Serializable {
    private TCPObjectType type;
    private Object object;

    public TCPObject(TCPObjectType type, Object object) {
        this.type = type;
        this.object = object;
    }

    public TCPObjectType getType() {
        return type;
    }

    public void setType(TCPObjectType type) {
        this.type = type;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
