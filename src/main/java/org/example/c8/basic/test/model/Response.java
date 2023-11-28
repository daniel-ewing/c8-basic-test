package org.example.c8.basic.test.model;

import java.util.ArrayList;
import java.util.List;

public class Response {
    private static final long serialVersionUID = 1L;
    private final List<ResponseItem> items;

    public Response() {
        this.items = initItems();
    }

    private List<ResponseItem> initItems() {
        return new ArrayList<>();
    }

}
