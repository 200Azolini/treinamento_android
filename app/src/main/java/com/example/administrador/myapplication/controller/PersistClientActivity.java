package com.example.administrador.myapplication.controller;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrador.myapplication.R;
import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.model.entities.ClientAddress;
import com.example.administrador.myapplication.model.services.CepService;
import com.example.administrador.myapplication.util.FormHelper;

public class PersistClientActivity extends AppCompatActivity{

    public static String CLIENT_PARAM = "CLIENT_PARAM";
    private Client client;
    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextPhoneNumber;
    private EditText cep;
    private Button buttonFindCep;
    private EditText editTextTipoDeLogradouro;
    private EditText editTextLogradouro;
    private EditText editTextBairro;
    private EditText editTextCidade;
    private EditText editTextEstado;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persist_client);

        bindFields();
        bindCepButton();

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            client = extras.getParcelable(CLIENT_PARAM);
            if (client == null) {
                throw new IllegalArgumentException();
            }
            bindForm(client);
        }

    }

    private void bindFields() {
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        cep = (EditText) findViewById(R.id.cep);
        editTextTipoDeLogradouro = (EditText) findViewById(R.id.editTextTipoDeLogradouro);
        editTextLogradouro = (EditText) findViewById(R.id.editTextLogradouro);
        editTextBairro = (EditText) findViewById(R.id.editTextBairro);
        editTextCidade = (EditText) findViewById(R.id.editTextCidade);
        editTextEstado = (EditText) findViewById(R.id.editTextEstado);
    }

    private void bindCepButton(){
        cep = (EditText) findViewById(R.id.cep);
        buttonFindCep = (Button) findViewById(R.id.buttonFindCep);
        buttonFindCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getAddressByCep().execute((cep.getText().toString()));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_client_persist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.menu_save){
            if(FormHelper.requiredValidate(PersistClientActivity.this , editTextName, editTextAge, editTextPhoneNumber)){
                bindClient();
                client.save();
                Toast.makeText(PersistClientActivity.this, R.string.success, Toast.LENGTH_LONG).show();
                PersistClientActivity.this.finish();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void bindClient(){
        if(client == null){
            client = new Client();
        }
        client.setName(editTextName.getText().toString());
        client.setAge(Integer.valueOf(editTextAge.getText().toString()));
        ClientAddress address = new ClientAddress();
        client.setAddress(address);
        client.setPhoneNumber(Integer.valueOf(editTextPhoneNumber.getText().toString()));
    }

    private void bindForm(Client client){
        editTextName.setText(client.getName());
        editTextAge.setText(client.getAge().toString());
        editTextPhoneNumber.setText(client.getPhoneNumber().toString());
    }

    private class getAddressByCep extends AsyncTask<String, Void, ClientAddress>{
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(PersistClientActivity.this);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show();
        }

        @Override
        protected ClientAddress doInBackground(String... params) {
            return CepService.getAddressBy(params[0]);
        }

        @Override
        protected void onPostExecute(ClientAddress clientAddress) {
            editTextTipoDeLogradouro.setText((clientAddress.getTipoDeLogradouro()));
            editTextLogradouro.setText((clientAddress.getLogradouro()));
            editTextBairro.setText((clientAddress.getBairro()));
            editTextCidade.setText((clientAddress.getCidade()));
            editTextEstado.setText((clientAddress.getEstado()));
            progressDialog.dismiss();
        }
    }

}
