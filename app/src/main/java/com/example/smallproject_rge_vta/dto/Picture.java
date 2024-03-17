package com.example.smallproject_rge_vta.dto;

public class Picture {
    public String id;
    public String contentB64;

    public Picture() {}

    public Picture(String id, String contentB64) {
        this.id = id;
        this.contentB64 = contentB64;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContentB64() {
        return contentB64;
    }

    public void setContentB64(String contentB64) {
        this.contentB64 = contentB64;
    }
}
