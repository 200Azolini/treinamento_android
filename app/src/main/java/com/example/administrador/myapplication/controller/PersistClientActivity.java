package com.example.administrador.myapplication.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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
        editTextName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_edittext_client, 0);
        editTextName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editTextName.getRight() - editTextName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        final Intent goToSOContacts = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        goToSOContacts.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                        startActivityForResult(goToSOContacts, 999);
                    }
                }
                return false;
            }
        });
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        cep = (EditText) findViewById(R.id.cep);
        editTextTipoDeLogradouro = (EditText) findViewById(R.id.editTextTipoDeLogradouro);
        editTextLogradouro = (EditText) findViewById(R.id.editTextLogradouro);
        editTextBairro = (EditText) findViewById(R.id.editTextBairro);
        editTextCidade = (EditText) findViewById(R.id.editTextCidade);
        editTextEstado = (EditText) findViewById(R.id.editTextEstado);
    }

    /**
     * @see <a href="http://developer.android.com/training/basics/intents/result.html">Getting a Result from an Activity</a>
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 999) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final Uri contactUri = data.getData();
                    final String[] projection = {
                            ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    };
                    final Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                    cursor.moveToFirst();

                    editTextName.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME)));
                    editTextPhoneNumber.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

                    cursor.close();
                } catch (Exception e) {
                    Log.d("TAG", "Unexpected error");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
        if(item.getItemId() == R.id.menu_save) {
            if(FormHelper.requiredValidate(PersistClientActivity.this, editTextName, editTextAge, editTextPhoneNumber)){
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
        client.setPhoneNumber(Integer.valueOf(editTextPhoneNumber.getText().toString()));
        ClientAddress address = new ClientAddress();
        address.setCep(cep.getText().toString());
        address.setTipoDeLogradouro(editTextTipoDeLogradouro.getText().toString());
        address.setLogradouro(editTextLogradouro.getText().toString());
        address.setBairro(editTextBairro.getText().toString());
        address.setCidade(editTextCidade.getText().toString());
        address.setEstado(editTextEstado.getText().toString());
        client.setAddress(address);
    }

    private void bindForm(Client client){
        editTextName.setText(client.getName());
        editTextAge.setText(client.getAge().toString());
        editTextPhoneNumber.setText(client.getPhoneNumber().toString());
        cep.setText(client.getAddress().getCep().toString());
        editTextTipoDeLogradouro.setText(client.getAddress().getTipoDeLogradouro().toString());
        editTextLogradouro.setText(client.getAddress().getLogradouro().toString());
        editTextBairro.setText(client.getAddress().getBairro().toString());
        editTextCidade.setText(client.getAddress().getCidade().toString());
        editTextEstado.setText(client.getAddress().getEstado().toString());
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
