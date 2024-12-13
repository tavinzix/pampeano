package com.example.pampeano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class cadastro extends AppCompatActivity {
    TextInputLayout nome, senha, csenha;
    Button cadastrar;
    ImageView voltar, assina;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.black));

        nome = (TextInputLayout) findViewById(R.id.bd_nome);
        senha = (TextInputLayout) findViewById(R.id.bd_senha);
        csenha = (TextInputLayout) findViewById(R.id.bd_csenha);
        cadastrar = (Button) findViewById(R.id.bd_bcadastro);
        voltar = (ImageView) findViewById(R.id.voltar);
        assina = (ImageView) findViewById(R.id.assinatura);
        DB = new DBHelper(this);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cadastra e volta para o login
                String user = nome.getEditText().getText().toString();
                String pass = senha.getEditText().getText().toString();
                String cpass = csenha.getEditText().getText().toString();

                if (user.equals("") || pass.equals("") || cpass.equals("")) {
                    Toast.makeText(cadastro.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass.equals(cpass)) {
                        Boolean checkUser = DB.checkUsuario(user);
                        if (checkUser == false) {
                            Boolean insert = DB.insereBanco(user, pass);
                            if (insert == true) {
                                Toast.makeText(cadastro.this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(cadastro.this, login.class);
                                startActivity(intent);
                                finishAffinity();
                            } else {
                                Toast.makeText(cadastro.this, "Algo deu errado", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(cadastro.this, "Usuario ja existe", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(cadastro.this, "Senhas não são iguais", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cadastro.this, login.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        assina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}