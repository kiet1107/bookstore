package com.example.bookstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SoldBookAdapter extends ArrayAdapter<SoldBook> {
    private Context context;
    private List<SoldBook> soldBooks;

    public SoldBookAdapter(Context context, List<SoldBook> soldBooks) {
        super(context, 0, soldBooks);
        this.context = context;
        this.soldBooks = soldBooks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_sold_book, parent, false);
        }

        SoldBook soldBook = soldBooks.get(position);

        TextView textBookTitle = view.findViewById(R.id.textBookTitle);
        TextView textSoldQuantity = view.findViewById(R.id.textSoldQuantity);

        textBookTitle.setText(soldBook.getTitle());
        textSoldQuantity.setText(context.getString(R.string.book_quantity) + ": " + soldBook.getQuantity());

        return view;
    }
}