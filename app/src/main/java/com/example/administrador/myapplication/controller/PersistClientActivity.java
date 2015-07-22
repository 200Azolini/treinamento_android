package com.example.administrador.myapplication.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrador.myapplication.R;
import com.example.administrador.myapplication.model.entities.Client;

public class PersistClientActivity extends AppCompatActivity{

    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextAddress;
    private EditText editTextPhoneNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persist_client);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_client_persist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.menu_save){
            Client client = bindClient();
            client.save();
            Toast.makeText(PersistClientActivity.this, Client.getAll().toString(), Toast.LENGTH_LONG).show();

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private Client bindClient(){
        Client client = new Client();
        client.setName(editTextName.getText().toString());
        client.setAge(Integer.valueOf(editTextAge.getText().toString()));
        client.setAddress(editTextAddress.getText().toString());
        client.setPhoneNumber(editTextPhoneNumber.getText().toString());
        return client;
    }

}
