package com.example.administrador.myapplication.controller;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrador.myapplication.R;
import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.util.FormHelper;

public class PersistClientActivity extends AppCompatActivity{

    public static String CLIENT_PARAM = "CLIENT_PARAM";
    private Client client;
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

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            client = extras.getParcelable(CLIENT_PARAM);
            if (client == null) {
                throw new IllegalArgumentException();
            }
            bindForm(client);
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_client_persist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.menu_save){
            if(FormHelper.requiredValidate(PersistClientActivity.this , editTextName, editTextAge, editTextAddress, editTextPhoneNumber)){
                bindClient();
                client.save();
                Toast.makeText(PersistClientActivity.this, R.string.success, Toast.LENGTH_LONG).show();
                PersistClientActivity.this.finish();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void bindClient(){
        client = new Client();
        client.setName(editTextName.getText().toString());
        client.setAge(Integer.valueOf(editTextAge.getText().toString()));
        client.setAddress(editTextAddress.getText().toString());
        client.setPhoneNumber(editTextPhoneNumber.getText().toString());
    }

    private void bindForm(Client client){
        editTextName.setText(client.getName());
        editTextAge.setText(client.getAge().toString());
        editTextAddress.setText(client.getAddress());
        editTextPhoneNumber.setText(client.getPhoneNumber().toString());
    }

}
