package com.example.bookstore;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class RevenueActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private ListView listViewSoldBooks;
    private TextView textTotalRevenue;
    private SoldBookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue);

        dbHelper = new DatabaseHelper(this);
        textTotalRevenue = findViewById(R.id.textTotalRevenue);
        listViewSoldBooks = findViewById(R.id.listViewSoldBooks);

        // Hiển thị tổng doanh thu
        double totalRevenue = dbHelper.getTotalRevenue();
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        textTotalRevenue.setText(getString(R.string.total_revenue) + ": " + formatter.format(totalRevenue) + " VND");

        // Hiển thị danh sách sách đã bán
        List<SoldBook> soldBooks = dbHelper.getSoldBooks();
        adapter = new SoldBookAdapter(this, soldBooks);
        listViewSoldBooks.setAdapter(adapter);
    }
}