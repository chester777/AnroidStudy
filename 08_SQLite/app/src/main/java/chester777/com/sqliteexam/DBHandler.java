package chester777.com.sqliteexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "productDB.db";
    public static final String DATABASE_TABLE = "products";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCTNAME = "productName";
    public static final String COLUMN_QUANTITY = "quantity";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PRODUCTNAME + " TEXT, " + COLUMN_QUANTITY + " INTEGER);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + DATABASE_TABLE + ";";
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void addProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.getProductName());
        values.put(COLUMN_QUANTITY, product.getQuantity());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(DATABASE_TABLE, null, values);
        db.close();
    }

    public Product findProduct(String productName) {

        String query = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + COLUMN_PRODUCTNAME + " = \'" + productName + "\';";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Product product = new Product();

        if(cursor.moveToFirst()) {
            product.setId(Integer.parseInt(cursor.getString(0)));
            product.setProductName(cursor.getString(1));
            product.setQuantity(Integer.parseInt(cursor.getString(2)));
            cursor.close();
            db.close();
            return product;
        }
        else {
            db.close();
            return null;
        }
    }

    public boolean delProduct(String productName) {
        boolean result = false;
        String query = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + COLUMN_PRODUCTNAME + "= \'" + productName + "\';";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Product product = new Product();

        if(cursor.moveToFirst()) {
            product.setId(Integer.parseInt(cursor.getString(0)));
            db.delete(DATABASE_TABLE, COLUMN_ID + "=?", new String[]{String.valueOf(product.getId())});
            cursor.close();
            db.close();
            return true;
        } else {
            db.close();
            return result;
        }
    }

    public boolean updateProduct(Product newProduct) {
        Product product = findProduct(newProduct.getProductName());

        if(product != null) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_PRODUCTNAME, product.getProductName());
            values.put(COLUMN_QUANTITY, newProduct.getQuantity());

            db.update(DATABASE_TABLE, values, COLUMN_ID + "=\'" + product.getId() + "\'", null);
            db.close();
            return true;
        }

        return false;
    }

    public Cursor findAll() {
        String query = "SELECT * FROM " + DATABASE_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(query, null);
    }
}
