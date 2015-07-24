package com.example.administrador.myapplication.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.administrador.myapplication.model.entities.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientContract {

    public static final String TABLE = "client";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String AGE = "age";
    public static final String ADDRESS = "address";
    public static final String PHONENUMBER = "phonenumber";
    public static final String[] COLUMNS = {ID, NAME, AGE, ADDRESS, PHONENUMBER};

    public static String getSqlCreateTable(){
        StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(NAME + " TEXT, ");
        sql.append(AGE + " INTEGER, ");
        sql.append(ADDRESS + " TEXT, ");
        sql.append(PHONENUMBER + " TEXT ");
        sql.append(" ); ");
        return sql.toString();
    }

    public static ContentValues getContentValues(Client client){
        ContentValues values = new ContentValues();
        values.put(NAME, client.getName());
        values.put(AGE, client.getAge());
        values.put(ADDRESS, client.getAddress());
        values.put(PHONENUMBER, client.getPhoneNumber());

        return values;
    }

    public static Client bind(Cursor cursor){
        if(!cursor.isBeforeFirst() || cursor.moveToNext()) {
            Client client = new Client();
            client.setId(cursor.getInt(cursor.getColumnIndex(ClientContract.ID)));
            client.setName(cursor.getString(cursor.getColumnIndex(ClientContract.NAME)));
            client.setAge(cursor.getInt(cursor.getColumnIndex(ClientContract.AGE)));
            client.setAddress(cursor.getString(cursor.getColumnIndex(ClientContract.ADDRESS)));
            client.setPhoneNumber(cursor.getInt(cursor.getColumnIndex(ClientContract.PHONENUMBER)));
            return client;
        }
        return null;
    }

    public static List<Client> bindList(Cursor cursor){
        final List<Client> serviceOrders = new ArrayList<Client>();
        while(cursor.moveToNext()){
            serviceOrders.add(bind(cursor));
        }
        return serviceOrders;
    }

}
