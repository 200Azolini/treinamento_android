package com.example.administrador.myapplication.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.util.AppUtil;

import java.util.List;

public class SQLiteClientRepository implements ClientRepository{

    private static SQLiteClientRepository singletonInstance;

    private SQLiteClientRepository(){
        super();
    }

    public static ClientRepository getInstance(){
        if(SQLiteClientRepository.singletonInstance == null){
            SQLiteClientRepository.singletonInstance = new SQLiteClientRepository();
        }
        return SQLiteClientRepository.singletonInstance;
    }

    @Override
    public void save(Client client) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = ClientContract.getContentValues(client);

        if(client.getId() == null){
            db.insert(ClientContract.TABLE, null, values);
        }
        else{
            values.put(ClientContract.ID, client.getId());
            String where = ClientContract.ID + " = ?";
            String[] args = {client.getId().toString()};
            db.update(ClientContract.TABLE, values, where, args);
        }

        db.close();
        helper.close();
    }

    @Override
    public List<Client> getAll() {
        List<Client> clients;

        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(ClientContract.TABLE, ClientContract.COLUMNS, null, null, null, null, ClientContract.NAME);

        clients = ClientContract.bindList(cursor);

        db.close();
        helper.close();
        return clients;
    }

    @Override
    public void delete(Client client) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();

        String where = ClientContract.ID + " = ?";
        String[] args = {client.getId().toString()};
        db.delete(ClientContract.TABLE, where, args);
    }
}
