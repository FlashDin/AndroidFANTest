package com.flashdin.fastandroidnetworkingnew.model.config;

public enum config {
    restUrl("http://192.168.57.1:8090/");

    private String url;

    config(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
