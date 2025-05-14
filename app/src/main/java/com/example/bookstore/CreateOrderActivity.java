package com.example.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CreateOrderActivity extends AppCompatActivity {
    private Spinner spinnerBook, spinnerVoucher;
    private EditText editOrderQuantity;
    private DatabaseHelper dbHelper;
    private List<Book> bookList;
    private List<Voucher> voucherList;
    private static final int REQUEST_CODE_ORDER_RECEIPT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        spinnerBook = findViewById(R.id.spinnerBook);
        spinnerVoucher = findViewById(R.id.spinnerVoucher);
        editOrderQuantity = findViewById(R.id.editOrderQuantity);
        Button buttonCreateOrder = findViewById(R.id.buttonCreateOrder);

        dbHelper = new DatabaseHelper(this);

        // Load danh sách sách
        bookList = dbHelper.getAllBooks();
        List<String> bookTitles = new ArrayList<>();
        for (Book book : bookList) {
            bookTitles.add(book.getTitle());
        }
        ArrayAdapter<String> bookAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bookTitles);
        bookAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBook.setAdapter(bookAdapter);

        // Load danh sách voucher
        voucherList = dbHelper.getAllVouchers();
        List<String> voucherNames = new ArrayList<>();
        voucherNames.add(getString(R.string.no_voucher));
        for (Voucher voucher : voucherList) {
            voucherNames.add(voucher.getName());
        }
        ArrayAdapter<String> voucherAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, voucherNames);
        voucherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVoucher.setAdapter(voucherAdapter);

        // Xử lý nút Tạo đơn hàng
        buttonCreateOrder.setOnClickListener(v -> createOrder());
    }

    private void createOrder() {
        String quantityStr = editOrderQuantity.getText().toString().trim();

        // Kiểm tra đầu vào
        if (quantityStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                Toast.makeText(this, "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                return;
            }

            int bookPosition = spinnerBook.getSelectedItemPosition();
            Book selectedBook = bookList.get(bookPosition);
            if (selectedBook.getQuantity() < quantity) {
                Toast.makeText(this, "Số lượng sách trong kho không đủ", Toast.LENGTH_SHORT).show();
                return;
            }

            int voucherPosition = spinnerVoucher.getSelectedItemPosition();
            Integer voucherId = null;
            double discount = 0;
            if (voucherPosition > 0) { // Không chọn "Không áp dụng voucher"
                Voucher selectedVoucher = voucherList.get(voucherPosition - 1);
                if (selectedVoucher.getQuantity() <= 0) {
                    Toast.makeText(this, "Voucher đã hết", Toast.LENGTH_SHORT).show();
                    return;
                }
                voucherId = selectedVoucher.getId();
                discount = selectedVoucher.getDiscount();
            }

            // Tính tổng tiền
            double totalPrice = (selectedBook.getPrice() * quantity) - discount;
            if (totalPrice < 0) totalPrice = 0;

            // Lấy thời gian hiện tại
            String createdAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

            // Cập nhật số lượng sách và voucher
            if (!dbHelper.updateBookQuantity(selectedBook.getId(), quantity)) {
                Toast.makeText(this, "Lỗi khi cập nhật số lượng sách", Toast.LENGTH_SHORT).show();
                return;
            }
            if (voucherId != null && !dbHelper.updateVoucherQuantity(voucherId)) {
                Toast.makeText(this, "Lỗi khi cập nhật số lượng voucher", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lưu đơn hàng
            long orderId = dbHelper.createOrder(selectedBook.getId(), quantity, voucherId, totalPrice, createdAt);
            if (orderId != -1) {
                Toast.makeText(this, "Tạo đơn hàng thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreateOrderActivity.this, OrderReceiptActivity.class);
                intent.putExtra("order_id", (int) orderId);
                startActivityForResult(intent, REQUEST_CODE_ORDER_RECEIPT);
            } else {
                Toast.makeText(this, "Lỗi khi tạo đơn hàng", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Số lượng phải là số", Toast.LENGTH_SHORT).show();
        }
    }
}