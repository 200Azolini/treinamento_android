package com.example.administrador.myapplication.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.R;
import com.melnykov.fab.FloatingActionButton;

import org.apache.http.protocol.HTTP;

import java.util.List;

public class ClientListActivity extends AppCompatActivity {

    private ListView listViewClients;
    private Client client;
    private FloatingActionButton fabAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindClientList();
        bindFab();
    }

    private void bindFab() {
        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToPersist = new Intent(ClientListActivity.this, PersistClientActivity.class);
                startActivity(goToPersist);
            }
        });
    }

    private void bindClientList() {
        List<Client> clientNames = Client.getAll();

        listViewClients = (ListView) findViewById(R.id.listViewCliente);

        ClientListAdapter clientAdapter = new ClientListAdapter(ClientListActivity.this, clientNames);

        listViewClients.setAdapter(clientAdapter);

        listViewClients.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                client = (Client) parent.getItemAtPosition(position);
                return false;
            }
        });
        listViewClients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Client client = (Client) parent.getItemAtPosition(position);
                // Best Practices: http://stackoverflow.com/questions/4275678/how-to-make-phone-call-using-intent-in-android
                final Intent goToSOPhoneCall = new Intent(Intent.ACTION_DIAL /* or Intent.ACTION_DIAL (no manifest permission needed) */);
                goToSOPhoneCall.setData(Uri.parse("tel:" + client.getPhoneNumber()));
                startActivity(goToSOPhoneCall);
            }
        });
        registerForContextMenu(listViewClients);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshClientList();
    }

    private void refreshClientList() {
        ClientListAdapter adapter = (ClientListAdapter) listViewClients.getAdapter();
        adapter.setClients(Client.getAll());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.adicionar){
            // Create the text message with a string
            final Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Seu texto aqui...");
            sendIntent.setType(HTTP.PLAIN_TEXT_TYPE);

            // Create intent to show the chooser dialog
            final Intent chooser = Intent.createChooser(sendIntent, "Titulo Chooser");

            // Verify the original intent will resolve to at least one activity
            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(chooser);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_client_list_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_edit){
            Intent intent = new Intent(ClientListActivity.this, PersistClientActivity.class);
            intent.putExtra(PersistClientActivity.CLIENT_PARAM, (Parcelable) client);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.menu_delete){
            new AlertDialog.Builder(ClientListActivity.this).setMessage(R.string.confirm_delete)
                    .setTitle(R.string.confirm)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            client.delete();
                            refreshClientList();
                            Toast.makeText(ClientListActivity.this, R.string.success, Toast.LENGTH_LONG).show();
                        }
                    }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id){
                            dialog.cancel();
                        }
                    }).create().show();
        }
        return super.onContextItemSelected(item);
    }

}

