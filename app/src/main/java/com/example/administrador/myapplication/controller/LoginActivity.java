package com.example.administrador.myapplication.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrador.myapplication.R;
import com.example.administrador.myapplication.model.entities.User;
import com.example.administrador.myapplication.model.persistence.UserContract;
import com.example.administrador.myapplication.util.FormHelper;

public class LoginActivity extends AppCompatActivity{

    private User user;
    private EditText editTextUserName;
    private EditText editTextPassword;
    Button buttonLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindFields();
        bindLoginButton();

    }

    private void bindFields() {
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
    }

    private void bindLoginButton() {
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        final UserContract contract = new UserContract();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FormHelper.requiredValidate(LoginActivity.this, editTextUserName, editTextPassword)) {
                    if (contract.verifyAccount()) {
                        Intent goToMainActivity = new Intent(LoginActivity.this, ClientListActivity.class);
                        startActivity(goToMainActivity);
                    }
                }
            }
        });
    }

}
