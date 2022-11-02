package org.example.c8.basic.test.model;

import java.io.Serializable;

public class InvoiceItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private int quantity;
    private String description;
    private Double costEach;

    public InvoiceItem(int quantity, String description, Double costEach) {
        this.quantity = quantity;
        this.description = description;
        this.costEach = costEach;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCostEach() {
        return costEach;
    }

    public void setCostEach(Double costEach) {
        this.costEach = costEach;
    }
}
