package org.example.c8.basic.test.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Invoice implements Serializable {
    private static final long serialVersionUID = 1L;
    private Address billingAddress;
    private Address shippingAddress;
    private final List<InvoiceLine> items;
    private Double costTotal;

    public Invoice() {
        items = initItems();
    }

    public Invoice(Address billingAddress) {
        this.billingAddress = billingAddress;
        this.shippingAddress = billingAddress;
        items = initItems();
    }

    public Invoice(Address billingAddress, Address shippingAddress) {
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
        items = initItems();
    }

    private List<InvoiceLine> initItems() {
        costTotal = 0D;
        return new ArrayList<>();
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<InvoiceLine> getItems() {
        return items;
    }

    public Double getCostTotal() {
        return costTotal;
    }

    private void setCostTotal() {
        costTotal = 0D;
        for (int i = 0; i < items.size(); i++) {
            costTotal += items.get(i).getCostLine();
        }
    }

    public void addItem(int quantity, String description, Double costEach) {
        items.add(new InvoiceLine(quantity, description, costEach));
        setCostTotal();
    }
    public void clearItems() {
        items.clear();
        costTotal = 0D;
    }
}
