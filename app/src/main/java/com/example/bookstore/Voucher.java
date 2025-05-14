package com.example.bookstore;

public class Voucher {
    private int id;
    private String name;
    private double discount;
    private int quantity;

    public Voucher(int id, String name, double discount, int quantity) {
        this.id = id;
        this.name = name;
        this.discount = discount;
        this.quantity = quantity;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getDiscount() { return discount; }
    public int getQuantity() { return quantity; }
}