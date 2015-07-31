package com.example.administrador.myapplication.model.persistence;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrador.myapplication.model.entities.User;
import com.example.administrador.myapplication.util.AppUtil;

public class SQLiteUserRepository implements UserRepository{

    private static SQLiteUserRepository singletonInstance;

    private SQLiteUserRepository(){
        super();
    }

    public static UserRepository getInstance(){
        if(SQLiteUserRepository.singletonInstance == null){
            SQLiteUserRepository.singletonInstance = new SQLiteUserRepository();
        }
        return SQLiteUserRepository.singletonInstance;
    }

    @Override
    public void save(User user) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = UserContract.getContentValues();

        if(user.getId() == null){
            db.insert(ClientContract.TABLE, null, values);
        }
        db.close();
        helper.close();
    }

}
