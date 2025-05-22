package com.example.bookstore;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookDetailActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        dbHelper = new DatabaseHelper(this);

        int bookId = getIntent().getIntExtra("book_id", -1);
        Book book = dbHelper.getBookById(bookId);

        if (book != null) {
            ImageView imageDetailBook = findViewById(R.id.imageDetailBook);
            TextView textDetailTitle = findViewById(R.id.textDetailTitle);
            TextView textDetailQuantity = findViewById(R.id.textDetailQuantity);
            TextView textDetailPrice = findViewById(R.id.textDetailPrice);
            TextView textDetailGenre = findViewById(R.id.textDetailGenre);

            textDetailTitle.setText(getString(R.string.book_title) + ": " + book.getTitle());
            textDetailQuantity.setText(getString(R.string.book_quantity) + ": " + book.getQuantity());
            textDetailPrice.setText(getString(R.string.book_price) + ": " + book.getPrice() + " VND");
            textDetailGenre.setText(getString(R.string.book_genre) + ": " + book.getGenre());

            int resId = getResources().getIdentifier(book.getImage(), "drawable", getPackageName());
            if (resId != 0) {
                imageDetailBook.setImageResource(resId);
            } else {
                imageDetailBook.setImageResource(R.drawable.coxanh);
            }
        }
    }
}