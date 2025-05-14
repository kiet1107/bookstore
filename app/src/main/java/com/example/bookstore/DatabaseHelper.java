package com.example.bookstore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BookDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_BOOKS = "books";
    private static final String TABLE_VOUCHERS = "vouchers";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_PRICE = "price";
    private static final String KEY_GENRE = "genre";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_VOUCHER_NAME = "voucher_name";
    private static final String KEY_VOUCHER_DISCOUNT = "discount";
    private static final String KEY_VOUCHER_QUANTITY = "quantity";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOKS_TABLE = "CREATE TABLE " + TABLE_BOOKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_QUANTITY + " INTEGER,"
                + KEY_PRICE + " REAL,"
                + KEY_GENRE + " TEXT,"
                + KEY_IMAGE + " TEXT" + ")";
        db.execSQL(CREATE_BOOKS_TABLE);

        String CREATE_VOUCHERS_TABLE = "CREATE TABLE " + TABLE_VOUCHERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_VOUCHER_NAME + " TEXT,"
                + KEY_VOUCHER_DISCOUNT + " REAL,"
                + KEY_VOUCHER_QUANTITY + " INTEGER" + ")";
        db.execSQL(CREATE_VOUCHERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOUCHERS);
        onCreate(db);
    }

    public long addOrUpdateBook(String title, int quantity, double price, String genre, String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Cursor cursor = db.query(TABLE_BOOKS, new String[]{KEY_ID, KEY_QUANTITY},
                KEY_TITLE + "=?", new String[]{title}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
            int currentQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_QUANTITY));
            values.put(KEY_QUANTITY, currentQuantity + quantity);
            cursor.close();
            long result = db.update(TABLE_BOOKS, values, KEY_ID + "=?", new String[]{String.valueOf(id)});
            db.close();
            return result;
        } else {
            if (cursor != null) cursor.close();
            values.put(KEY_TITLE, title);
            values.put(KEY_QUANTITY, quantity);
            values.put(KEY_PRICE, price);
            values.put(KEY_GENRE, genre);
            values.put(KEY_IMAGE, image);
            long id = db.insert(TABLE_BOOKS, null, values);
            db.close();
            return id;
        }
    }

    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_BOOKS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_QUANTITY));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_PRICE));
                String genre = cursor.getString(cursor.getColumnIndexOrThrow(KEY_GENRE));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGE));

                Book book = new Book(id, title, quantity, price, genre, image);
                bookList.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookList;
    }

    public Book getBookById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOKS, new String[]{KEY_ID, KEY_TITLE, KEY_QUANTITY, KEY_PRICE, KEY_GENRE, KEY_IMAGE},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Book book = new Book(
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_QUANTITY)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_PRICE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_GENRE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGE))
            );
            cursor.close();
            db.close();
            return book;
        }
        if (cursor != null) cursor.close();
        db.close();
        return null;
    }

    public long addVoucher(String name, double discount, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_VOUCHER_NAME, name);
        values.put(KEY_VOUCHER_DISCOUNT, discount);
        values.put(KEY_VOUCHER_QUANTITY, quantity);

        long id = db.insert(TABLE_VOUCHERS, null, values);
        db.close();
        return id;
    }

    public List<Voucher> getAllVouchers() {
        List<Voucher> voucherList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_VOUCHERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_VOUCHER_NAME));
                double discount = cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_VOUCHER_DISCOUNT));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_VOUCHER_QUANTITY));

                Voucher voucher = new Voucher(id, name, discount, quantity);
                voucherList.add(voucher);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return voucherList;
    }
}