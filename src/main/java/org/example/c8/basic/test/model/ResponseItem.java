package org.example.c8.basic.test.model;

public class ResponseItem {
    private static final long serialVersionUID = 1L;
    private String name;
    private String value;

    public ResponseItem(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
