package com.example.bookstore;

public class Book {
    private int id;
    private String title;
    private String author;
    private int quantity;
    private double price;
    private String genre;
    private String image;

    public Book(int id, String title,String author, int quantity, double price, String genre, String image) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.price = price;
        this.genre = genre;
        this.image = image;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor()  {return author; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public String getGenre() { return genre; }
    public String getImage() { return image; }
}