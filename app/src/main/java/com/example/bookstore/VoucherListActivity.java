package com.example.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class VoucherListActivity extends AppCompatActivity {
    private ListView listViewVouchers;
    private DatabaseHelper dbHelper;
    private VoucherAdapter adapter;
    private static final int REQUEST_CODE_ADD_VOUCHER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_list);

        listViewVouchers = findViewById(R.id.listViewVouchers);
        dbHelper = new DatabaseHelper(this);

        // Khởi tạo ListView
        updateVoucherList();

        // Xử lý nút Tạo voucher
        Button buttonAddVoucher = findViewById(R.id.buttonAddVoucher);
        buttonAddVoucher.setOnClickListener(v -> {
            Intent intent = new Intent(VoucherListActivity.this, AddVoucherActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_VOUCHER);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_VOUCHER && resultCode == RESULT_OK) {
            // Làm mới danh sách voucher sau khi thêm
            updateVoucherList();
        }
    }

    private void updateVoucherList() {
        List<Voucher> voucherList = dbHelper.getAllVouchers();
        if (adapter == null) {
            adapter = new VoucherAdapter(this, voucherList);
            listViewVouchers.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.addAll(voucherList);
            adapter.notifyDataSetChanged();
        }
    }
}