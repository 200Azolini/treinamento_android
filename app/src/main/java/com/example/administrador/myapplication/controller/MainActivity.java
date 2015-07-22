package com.example.administrador.myapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Client> clientNames = Client.getAll();


        ListView listViewClients = (ListView) findViewById(R.id.listViewCliente);

        ClientListAdapter clientAdapter = new ClientListAdapter(MainActivity.this, clientNames);

        listViewClients.setAdapter(clientAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.adicionar){
            Intent goToPersist = new Intent(MainActivity.this, PersistClientActivity.class);
            startActivity(goToPersist);
        }
        return super.onOptionsItemSelected(item);
    }
}
