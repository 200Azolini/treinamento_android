package com.example.administrador.myapplication.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.location.Address;

import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.model.entities.ClientAddress;

import java.util.ArrayList;
import java.util.List;

public class ClientContract {

    public static final String TABLE = "client";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String AGE = "age";
    public static final String PHONENUMBER = "phonenumber";
    public static final String CEP = "cep";
    public static final String TIPOLOGRADOURO = "tipologradouro";
    public static final String LOGRADOURO = "logradouro";
    public static final String BAIRRO = "bairro";
    public static final String CIDADE = "cidade";
    public static final String ESTADO = "estado";
    public static final String[] COLUMNS = {ID, NAME, AGE, PHONENUMBER, CEP, TIPOLOGRADOURO, LOGRADOURO, BAIRRO, CIDADE, ESTADO};

    public static String getSqlCreateTable(){
        StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(NAME + " TEXT, ");
        sql.append(AGE + " INTEGER, ");
        sql.append(PHONENUMBER + " TEXT, ");
        sql.append(CEP + " TEXT, ");
        sql.append(TIPOLOGRADOURO + " TEXT, ");
        sql.append(LOGRADOURO + " TEXT, ");
        sql.append(BAIRRO + " TEXT, ");
        sql.append(CIDADE + " TEXT, ");
        sql.append(ESTADO + " TEXT ");
        sql.append(" ); ");
        return sql.toString();
    }

    public static ContentValues getContentValues(Client client){
        ContentValues values = new ContentValues();
        values.put(NAME, client.getName());
        values.put(AGE, client.getAge());
        values.put(PHONENUMBER, client.getPhoneNumber());
        values.put(CEP, client.getAddress().getCep());
        values.put(TIPOLOGRADOURO, client.getAddress().getTipoDeLogradouro());
        values.put(LOGRADOURO, client.getAddress().getLogradouro());
        values.put(BAIRRO, client.getAddress().getBairro());
        values.put(CIDADE, client.getAddress().getCidade());
        values.put(ESTADO, client.getAddress().getEstado());
        return values;
    }

    public static Client bind(Cursor cursor){
        if(!cursor.isBeforeFirst() || cursor.moveToNext()) {
            ClientAddress address = new ClientAddress();
            Client client = new Client();
            client.setId(cursor.getInt(cursor.getColumnIndex(ClientContract.ID)));
            client.setName(cursor.getString(cursor.getColumnIndex(ClientContract.NAME)));
            client.setAge(cursor.getInt(cursor.getColumnIndex(ClientContract.AGE)));
            client.setPhoneNumber(cursor.getInt(cursor.getColumnIndex(ClientContract.PHONENUMBER)));
            address.setCep(cursor.getString(cursor.getColumnIndex(ClientContract.CEP)));
            address.setTipoDeLogradouro(cursor.getString(cursor.getColumnIndex(ClientContract.TIPOLOGRADOURO)));
            address.setLogradouro(cursor.getString(cursor.getColumnIndex(ClientContract.LOGRADOURO)));
            address.setBairro(cursor.getString(cursor.getColumnIndex(ClientContract.BAIRRO)));
            address.setCidade(cursor.getString(cursor.getColumnIndex(ClientContract.CIDADE)));
            address.setEstado(cursor.getString(cursor.getColumnIndex(ClientContract.ESTADO)));
            client.setAddress(address);
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
