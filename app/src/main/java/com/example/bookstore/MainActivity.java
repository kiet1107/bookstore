package com.example.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonViewBooks = findViewById(R.id.buttonViewBooks);
        buttonViewBooks.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BookListActivity.class);
            startActivity(intent);
        });

        Button buttonAddBook = findViewById(R.id.buttonAddBook);
        buttonAddBook.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
            startActivity(intent);
        });

        Button buttonRevenue = findViewById(R.id.buttonRevenue);
        buttonRevenue.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RevenueActivity.class);
            startActivity(intent);
        });

        Button buttonOffer = findViewById(R.id.buttonOffer);
        buttonOffer.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VoucherListActivity.class);
            startActivity(intent);
        });

        Button buttonCreateOrder = findViewById(R.id.buttonCreateOrder);
        buttonCreateOrder.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateOrderActivity.class);
            startActivity(intent);
        });
    }
}