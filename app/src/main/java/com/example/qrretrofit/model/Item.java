package com.example.qrretrofit.model;

import java.util.Map;

public class Item {

    private String id;
    private String name;
    private Map<String, Object> data;

    public Item() {
    }

    public String getId() {
        return id;
    }

    public Item setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Item setData(Map<String, Object> data) {
        this.data = data;
        return this;
    }
}