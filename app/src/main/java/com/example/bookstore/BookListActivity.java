package com.example.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class BookListActivity extends AppCompatActivity {
    private ListView listViewBooks;
    private DatabaseHelper dbHelper;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        listViewBooks = findViewById(R.id.listViewBooks);
        dbHelper = new DatabaseHelper(this);

        // Khởi tạo ListView
        updateBookList();

        // Xử lý click vào item trong ListView
        listViewBooks.setOnItemClickListener((parent, view, position, id) -> {
            Book selectedBook = (Book) parent.getItemAtPosition(position);
            Intent intent = new Intent(BookListActivity.this, BookDetailActivity.class);
            intent.putExtra("book_id", selectedBook.getId());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Làm mới danh sách khi quay lại
        updateBookList();
    }

    private void updateBookList() {
        List<Book> bookList = dbHelper.getAllBooks();
        if (adapter == null) {
            adapter = new BookAdapter(this, bookList);
            listViewBooks.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.addAll(bookList);
            adapter.notifyDataSetChanged();
        }
    }
}