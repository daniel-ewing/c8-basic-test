package org.example.c8.basic.test.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Invoice implements Serializable {
    private static final long serialVersionUID = 1L;
    private Address billingAddress;
    private Address shippingAddress;
    private final List<InvoiceItem> items;

    public Invoice() {
        this.items = initItems();
    }

    public Invoice(Address billingAddress) {
        this.billingAddress = billingAddress;
        this.shippingAddress = billingAddress;
        this.items = initItems();
    }

    public Invoice(Address billingAddress, Address shippingAddress) {
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
        this.items = initItems();
    }

    private List<InvoiceItem> initItems() {
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

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void addItem(int quantity, String description, Double costEach) {
        items.add(new InvoiceItem(quantity, description, costEach));
    }
}
