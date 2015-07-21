package com.example.administrador.myapplication.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        List<Client> clientNames = getClients();


        ListView listViewClients = (ListView) findViewById(R.id.listViewCliente);

        ClientListAdapter clientAdapter = new ClientListAdapter(MainActivity.this, clientNames);


        listViewClients.setAdapter(clientAdapter);
    }

    private List<Client> getClients() {
        List<Client> clients = new ArrayList<>();

        Client duzentos = new Client();
        duzentos.setName("Duzentos");
        duzentos.setAge(21);
        clients.add(duzentos);

        Client valdeco = new Client();
        valdeco.setName("Valdeco");
        valdeco.setAge(26);
        clients.add(valdeco);

        return clients;
    }
}
