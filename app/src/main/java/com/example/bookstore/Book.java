package com.example.bookstore;

public class Book {
    private int id;
    private String title;
    private int quantity;
    private double price;
    private String genre;
    private String image;

    public Book(int id, String title, int quantity, double price, String genre, String image) {
        this.id = id;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
        this.genre = genre;
        this.image = image;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public String getGenre() { return genre; }
    public String getImage() { return image; }
}