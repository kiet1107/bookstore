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
    private static final String TABLE_ORDERS = "orders";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_PRICE = "price";
    private static final String KEY_GENRE = "genre";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_VOUCHER_NAME = "voucher_name";
    private static final String KEY_VOUCHER_DISCOUNT = "discount";
    private static final String KEY_VOUCHER_QUANTITY = "quantity";
    private static final String KEY_BOOK_ID = "book_id";
    private static final String KEY_VOUCHER_ID = "voucher_id";
    private static final String KEY_TOTAL_PRICE = "total_price";
    private static final String KEY_CREATED_AT = "created_at";

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

        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_BOOK_ID + " INTEGER,"
                + KEY_QUANTITY + " INTEGER,"
                + KEY_VOUCHER_ID + " INTEGER,"
                + KEY_TOTAL_PRICE + " REAL,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_ORDERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOUCHERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
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

    public Voucher getVoucherById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_VOUCHERS, new String[]{KEY_ID, KEY_VOUCHER_NAME, KEY_VOUCHER_DISCOUNT, KEY_VOUCHER_QUANTITY},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Voucher voucher = new Voucher(
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_VOUCHER_NAME)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_VOUCHER_DISCOUNT)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_VOUCHER_QUANTITY))
            );
            cursor.close();
            db.close();
            return voucher;
        }
        if (cursor != null) cursor.close();
        db.close();
        return null;
    }

    public long createOrder(int bookId, int quantity, Integer voucherId, double totalPrice, String createdAt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BOOK_ID, bookId);
        values.put(KEY_QUANTITY, quantity);
        if (voucherId != null) {
            values.put(KEY_VOUCHER_ID, voucherId);
        }
        values.put(KEY_TOTAL_PRICE, totalPrice);
        values.put(KEY_CREATED_AT, createdAt);

        long id = db.insert(TABLE_ORDERS, null, values);
        db.close();
        return id;
    }

    public boolean updateBookQuantity(int bookId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Cursor cursor = db.query(TABLE_BOOKS, new String[]{KEY_QUANTITY},
                KEY_ID + "=?", new String[]{String.valueOf(bookId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int currentQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_QUANTITY));
            cursor.close();
            if (currentQuantity >= quantity) {
                values.put(KEY_QUANTITY, currentQuantity - quantity);
                int rows = db.update(TABLE_BOOKS, values, KEY_ID + "=?", new String[]{String.valueOf(bookId)});
                db.close();
                return rows > 0;
            }
            cursor.close();
        }
        db.close();
        return false;
    }

    public boolean updateVoucherQuantity(int voucherId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Cursor cursor = db.query(TABLE_VOUCHERS, new String[]{KEY_VOUCHER_QUANTITY},
                KEY_ID + "=?", new String[]{String.valueOf(voucherId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int currentQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_VOUCHER_QUANTITY));
            cursor.close();
            if (currentQuantity > 0) {
                values.put(KEY_VOUCHER_QUANTITY, currentQuantity - 1);
                int rows = db.update(TABLE_VOUCHERS, values, KEY_ID + "=?", new String[]{String.valueOf(voucherId)});
                db.close();
                return rows > 0;
            }
            cursor.close();
        }
        db.close();
        return false;
    }

    public Order getOrderById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ORDERS, new String[]{KEY_ID, KEY_BOOK_ID, KEY_QUANTITY, KEY_VOUCHER_ID, KEY_TOTAL_PRICE, KEY_CREATED_AT},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Integer voucherId = cursor.isNull(cursor.getColumnIndexOrThrow(KEY_VOUCHER_ID)) ? null : cursor.getInt(cursor.getColumnIndexOrThrow(KEY_VOUCHER_ID));
            Order order = new Order(
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_BOOK_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_QUANTITY)),
                    voucherId,
                    cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_TOTAL_PRICE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_CREATED_AT))
            );
            cursor.close();
            db.close();
            return order;
        }
        if (cursor != null) cursor.close();
        db.close();
        return null;
    }

    public double getTotalRevenue() {
        double totalRevenue = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT o." + KEY_BOOK_ID + ", o." + KEY_QUANTITY + ", b." + KEY_PRICE +
                " FROM " + TABLE_ORDERS + " o JOIN " + TABLE_BOOKS + " b ON o." + KEY_BOOK_ID + "=b." + KEY_ID;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_QUANTITY));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_PRICE));
                totalRevenue += price * quantity;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return totalRevenue;
    }

    public List<SoldBook> getSoldBooks() {
        List<SoldBook> soldBooks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT b." + KEY_TITLE + ", SUM(o." + KEY_QUANTITY + ") as total_quantity" +
                " FROM " + TABLE_ORDERS + " o JOIN " + TABLE_BOOKS + " b ON o." + KEY_BOOK_ID + "=b." + KEY_ID +
                " GROUP BY o." + KEY_BOOK_ID;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE));
                int totalQuantity = cursor.getInt(cursor.getColumnIndexOrThrow("total_quantity"));
                soldBooks.add(new SoldBook(title, totalQuantity));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return soldBooks;
    }
}