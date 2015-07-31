package com.example.administrador.myapplication.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrador.myapplication.model.entities.User;
import com.example.administrador.myapplication.util.AppUtil;

public class UserContract {

    public static final String TABLE = "user";
    public static final String ID = "id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String[] COLUMNS = {ID, USERNAME, PASSWORD};

    public static String getSqlCreateTable() {
        StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(USERNAME + " TEXT, ");
        sql.append(PASSWORD + " TEXT ");
        sql.append(" ); ");
        return sql.toString();
    }

    public static ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(USERNAME, "admin");
        values.put(PASSWORD, "admin");
        return values;
    }

    public static User bind(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            User user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex(UserContract.ID)));
            user.setUser(cursor.getString(cursor.getColumnIndex(UserContract.USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(UserContract.PASSWORD)));
            return user;
        }
        return null;
    }

    public static String createUserPadrao() {
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO ");
        sql.append(TABLE);
        sql.append(" VALUES ( ");
        sql.append(" 1, 'admin', 'admin' );");
        return sql.toString();
    }

    public static boolean verifyAccount(){
        boolean isValid = false;
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(UserContract.TABLE, UserContract.COLUMNS, " user.username = 'admin' AND user.password = 'admin' ", null, null, null, UserContract.USERNAME);
        if(cursor.getCount() == 1){
            isValid = true;
        }
        db.close();
        helper.close();
        return isValid;
    }

}
