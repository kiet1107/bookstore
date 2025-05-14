package com.example.bookstore;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddBookActivity extends AppCompatActivity {
    private EditText editTitle, editQuantity, editPrice, editGenre, editImage;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        // Khởi tạo các view
        editTitle = findViewById(R.id.editTitle);
        editQuantity = findViewById(R.id.editQuantity);
        editPrice = findViewById(R.id.editPrice);
        editGenre = findViewById(R.id.editGenre);
        editImage = findViewById(R.id.editImage);
        Button buttonSave = findViewById(R.id.buttonSave);

        dbHelper = new DatabaseHelper(this);

        // Xử lý nút Lưu
        buttonSave.setOnClickListener(v -> saveBook());
    }

    private void saveBook() {
        String title = editTitle.getText().toString().trim();
        String quantityStr = editQuantity.getText().toString().trim();
        String priceStr = editPrice.getText().toString().trim();
        String genre = editGenre.getText().toString().trim();
        String image = editImage.getText().toString().trim();

        // Kiểm tra dữ liệu đầu vào
        if (title.isEmpty() || quantityStr.isEmpty() || priceStr.isEmpty() || genre.isEmpty() || image.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            double price = Double.parseDouble(priceStr);
            if (quantity <= 0 || price < 0) {
                Toast.makeText(this, "Số lượng phải lớn hơn 0 và giá không âm", Toast.LENGTH_SHORT).show();
                return;
            }
            long result = dbHelper.addOrUpdateBook(title, quantity, price, genre, image);
            if (result != -1) {
                Toast.makeText(this, "Thêm/cập nhật sách thành công", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Lỗi khi thêm/cập nhật sách", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Số lượng và giá phải là số", Toast.LENGTH_SHORT).show();
        }
    }
}