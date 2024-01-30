package com.basejava.webapp.model;

public class Resume {

    private String uuid;

    public Resume(String uuid) {
        setUuid(uuid);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return uuid;
    }
}
