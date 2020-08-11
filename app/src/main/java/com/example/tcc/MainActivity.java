package com.example.tcc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class MainActivity extends AppCompatActivity {


    private Button Cadastro, BtnEntrar;
    private EditText editEmailLogar, editSenhaLogar;
    private String HOST = "http://192.168.2.122/login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editEmailLogar = (EditText) findViewById(R.id.editEmailLogar);
        editSenhaLogar = (EditText) findViewById(R.id.editSenhaLogar);
        BtnEntrar = (Button) findViewById(R.id.button);
        Cadastro = findViewById(R.id.button2);

        BtnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String email = editEmailLogar.getText().toString();
                String senha = editSenhaLogar.getText().toString();
                String url = HOST + "/logar.php";

                if(email.isEmpty() || senha.isEmpty()){
                    Toast.makeText(MainActivity.this, "Todos os campos são obrigatórios", Toast.LENGTH_LONG).show ();
                }

                Ion.with(MainActivity.this)
                        .load(url)

                        .setBodyParameter("email_app", email)
                        .setBodyParameter("senha_app", senha)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                try {
                                    String RETORNO = result.get("LOGIN").getAsString();

                                    if(RETORNO.equals("ERRO")){
                                        Toast.makeText(MainActivity.this, "Email ou senha incorretos", Toast.LENGTH_LONG).show ();
                                    } else if(RETORNO.equals("SUCESSO")) {
                                        Intent abreMainScreen = new Intent(MainActivity.this, MainScreen.class);
                                        startActivity(abreMainScreen);
                                    } else {
                                        Toast.makeText(MainActivity.this, "Ocorreu um erro", Toast.LENGTH_LONG).show ();
                                    }
                                } catch (Exception erro) {
                                    Toast.makeText(MainActivity.this, "Ocorreu um erro, " + erro, Toast.LENGTH_LONG).show ();
                                }
                            }
                        });
            }
        });

        Cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                movetoCadastro();
            }
        });



        }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void movetoCadastro(){
            Intent intent = new Intent(MainActivity.this, cadastro.class);
            startActivity(intent);
        }
}
