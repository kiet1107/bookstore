package com.example.bookstore;

public class SoldBook {
    private String title;
    private int quantity;

    public SoldBook(String title, int quantity) {
        this.title = title;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public int getQuantity() {
        return quantity;
    }
}