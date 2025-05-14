package com.example.bookstore;

public class Order {
    private int id;
    private int bookId;
    private int quantity;
    private Integer voucherId;
    private double totalPrice;
    private String createdAt;

    public Order(int id, int bookId, int quantity, Integer voucherId, double totalPrice, String createdAt) {
        this.id = id;
        this.bookId = bookId;
        this.quantity = quantity;
        this.voucherId = voucherId;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }

    // Getters
    public int getId() { return id; }
    public int getBookId() { return bookId; }
    public int getQuantity() { return quantity; }
    public Integer getVoucherId() { return voucherId; }
    public double getTotalPrice() { return totalPrice; }
    public String getCreatedAt() { return createdAt; }
}