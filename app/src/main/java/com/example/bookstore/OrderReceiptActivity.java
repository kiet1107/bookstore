package com.example.bookstore;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderReceiptActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_receipt);

        dbHelper = new DatabaseHelper(this);
        TextView textReceipt = findViewById(R.id.textReceipt);

        int orderId = getIntent().getIntExtra("order_id", -1);
        Order order = dbHelper.getOrderById(orderId);

        if (order != null) {
            Book book = dbHelper.getBookById(order.getBookId());
            Voucher voucher = order.getVoucherId() != null ? dbHelper.getVoucherById(order.getVoucherId()) : null;

            StringBuilder receipt = new StringBuilder();
            receipt.append("HÓA ĐƠN BÁN SÁCH\n");
            receipt.append("------------------------\n");
            receipt.append(getString(R.string.book_title)).append(": ").append(book.getTitle()).append("\n");
            receipt.append(getString(R.string.book_quantity)).append(": ").append(order.getQuantity()).append("\n");
            receipt.append(getString(R.string.book_price)).append(": ").append(book.getPrice()).append(" VND\n");
            if (voucher != null) {
                receipt.append(getString(R.string.voucher_name)).append(": ").append(voucher.getName()).append("\n");
                receipt.append(getString(R.string.voucher_discount)).append(": ").append(voucher.getDiscount()).append(" VND\n");
            } else {
                receipt.append(getString(R.string.voucher_name)).append(": Không áp dụng\n");
            }
            receipt.append(getString(R.string.total_price)).append(": ").append(order.getTotalPrice()).append(" VND\n");
            receipt.append(getString(R.string.created_at)).append(": ").append(order.getCreatedAt()).append("\n");
            receipt.append("------------------------\n");
            receipt.append("Cảm ơn bạn đã mua hàng!");

            textReceipt.setText(receipt.toString());
        } else {
            textReceipt.setText("Lỗi: Không tìm thấy hóa đơn");
        }
    }
}