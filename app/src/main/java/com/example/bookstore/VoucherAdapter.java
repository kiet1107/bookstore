package com.example.bookstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class VoucherAdapter extends ArrayAdapter<Voucher> {
    private Context context;
    private List<Voucher> vouchers;

    public VoucherAdapter(Context context, List<Voucher> vouchers) {
        super(context, 0, vouchers);
        this.context = context;
        this.vouchers = vouchers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_voucher, parent, false);
        }

        Voucher voucher = vouchers.get(position);

        TextView textVoucherName = view.findViewById(R.id.textVoucherName);
        TextView textVoucherDiscount = view.findViewById(R.id.textVoucherDiscount);
        TextView textVoucherQuantity = view.findViewById(R.id.textVoucherQuantity);

        textVoucherName.setText(voucher.getName());
        textVoucherDiscount.setText(context.getString(R.string.voucher_discount) + ": " + voucher.getDiscount() + " VND");
        textVoucherQuantity.setText(context.getString(R.string.voucher_quantity) + ": " + voucher.getQuantity());

        return view;
    }
}