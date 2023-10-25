package com.example.pampeano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class login extends AppCompatActivity {

    ImageView logo;
    TextView titulo;
    TextInputLayout user, senha;
    Button login, cadastrar;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logo = findViewById(R.id.logo_image);
        titulo = findViewById(R.id.logo_name);
        user = findViewById(R.id.user);
        senha = findViewById(R.id.senha);
        login = findViewById(R.id.login_btn);
        cadastrar = findViewById(R.id.cadastrarnovo);
        DB = new DBHelper(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = user.getEditText().getText().toString();
                String pass = senha.getEditText().getText().toString();

                if (usuario.equals("") || pass.equals("")) {
                    Toast.makeText(login.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkUserPass = DB.checkSenha(usuario, pass);
                    {
                        if (checkUserPass == true) {
                            Toast.makeText(login.this, "Entrou", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(login.this, MainActivity.class);
                            intent.putExtra("jurado", user.getEditText().getText().toString());
                            startActivity(intent);
                        } else {
                            Toast.makeText(login.this, "Senha errada", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        cadastrar.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, cadastro.class);
                startActivity(intent);
            }
        }));
    }
}