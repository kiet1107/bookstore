package com.example.bookstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    private Context context;
    private List<Book> books;

    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
        this.context = context;
        this.books = books;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        }

        Book book = books.get(position);

        ImageView imageBook = view.findViewById(R.id.imageBook);
        TextView textTitle = view.findViewById(R.id.textTitle);
        TextView textQuantity = view.findViewById(R.id.textQuantity);

        textTitle.setText(book.getTitle());
        textQuantity.setText(context.getString(R.string.book_quantity) + ": " + book.getQuantity());

        // Lấy ID của ảnh từ drawable
        int resId = context.getResources().getIdentifier(book.getImage(), "drawable", context.getPackageName());
        if (resId != 0) {
            imageBook.setImageResource(resId);
        } else {
            imageBook.setImageResource(R.drawable.dacnhantam);
        }

        return view;
    }
}