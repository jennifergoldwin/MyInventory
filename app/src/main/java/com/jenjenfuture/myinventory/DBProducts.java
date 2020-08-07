package com.jenjenfuture.myinventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBProducts {
    private DBHandler dbHandler;
    private Context ctx;

    public DBProducts(Context ctx){
        this.ctx = ctx;
        dbHandler = new DBHandler(ctx);
    }

    public void insertItem (Product product){
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBHandler.FIELD_PRODUCT_NAME,product.name);
        cv.put(DBHandler.FIELD_PRODUCT_DESC,product.desc);
        cv.put(DBHandler.FIELD_PRODUCT_QUANTITY,product.quantity);



        db.insert(DBHandler.TABLE_PRODUCTS,null,cv);
        db.close();

    }

    public void updateItem (Product product){
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBHandler.FIELD_PRODUCT_NAME,product.name);
        cv.put(DBHandler.FIELD_PRODUCT_DESC,product.desc);
        cv.put(DBHandler.FIELD_PRODUCT_QUANTITY,product.quantity);

        db.update(DBHandler.TABLE_PRODUCTS,cv,DBHandler.FIELD_PRODUCT_ID + " = ? ",new String[]{String.valueOf(product.id)});
        db.close();
    }

    public List<Product> getAllProducts(){
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        List<Product> productList = new ArrayList<>();

        String SELECT_QUERY = "SELECT * FROM " + DBHandler.TABLE_PRODUCTS;
        Cursor cursor = db.rawQuery(SELECT_QUERY,null);

        if (cursor.moveToFirst()){
            do{
                Product product = new Product();
                product.id = cursor.getInt(cursor.getColumnIndex(DBHandler.FIELD_PRODUCT_ID));
                product.name = cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_PRODUCT_NAME));
                product.desc = cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_PRODUCT_DESC));
                product.quantity = cursor.getInt(cursor.getColumnIndex(DBHandler.FIELD_PRODUCT_QUANTITY));
                productList.add(product);
            }while(cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return productList;
    }


}
