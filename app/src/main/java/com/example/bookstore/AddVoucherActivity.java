package com.example.bookstore;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddVoucherActivity extends AppCompatActivity {
    private EditText editVoucherName, editVoucherDiscount, editVoucherQuantity;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_voucher);

        // Khởi tạo các view
        editVoucherName = findViewById(R.id.editVoucherName);
        editVoucherDiscount = findViewById(R.id.editVoucherDiscount);
        editVoucherQuantity = findViewById(R.id.editVoucherQuantity);
        Button buttonSaveVoucher = findViewById(R.id.buttonSaveVoucher);

        dbHelper = new DatabaseHelper(this);

        // Xử lý nút Lưu
        buttonSaveVoucher.setOnClickListener(v -> saveVoucher());
    }

    private void saveVoucher() {
        String name = editVoucherName.getText().toString().trim();
        String discountStr = editVoucherDiscount.getText().toString().trim();
        String quantityStr = editVoucherQuantity.getText().toString().trim();

        // Kiểm tra dữ liệu đầu vào
        if (name.isEmpty() || discountStr.isEmpty() || quantityStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double discount = Double.parseDouble(discountStr);
            int quantity = Integer.parseInt(quantityStr);
            if (discount < 0 || quantity <= 0) {
                Toast.makeText(this, "Số tiền giảm không âm và số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                return;
            }
            long result = dbHelper.addVoucher(name, discount, quantity);
            if (result != -1) {
                Toast.makeText(this, "Tạo voucher thành công", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Lỗi khi tạo voucher", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Số tiền giảm và số lượng phải là số", Toast.LENGTH_SHORT).show();
        }
    }
}