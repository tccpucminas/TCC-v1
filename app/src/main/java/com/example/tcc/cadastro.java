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
import org.w3c.dom.Text;

public class cadastro extends AppCompatActivity {

    private EditText editNomeCad, editEmailCad, editSenhaCad, editSenhaConf;
    private Button btnCadastrar;
    private String HOST = "http://192.168.2.122/login";
    private Button voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        editNomeCad = (EditText) findViewById(R.id.editNomeCad);
        editEmailCad = (EditText) findViewById(R.id.editEmailCad);
        editSenhaCad = (EditText) findViewById(R.id.editSenhaCad);
        editSenhaConf = (EditText) findViewById(R.id.editSenhaConf);

        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = HOST + "/cadastrar.php";
                String nome = editNomeCad.getText().toString();
                String email = editEmailCad.getText().toString();
                String senha = editSenhaCad.getText().toString();
                String confirma = editSenhaConf.getText().toString();


                if(confirma.equals(senha)){

                    if(nome.isEmpty() || email.isEmpty() || senha.isEmpty()){
                        Toast.makeText(cadastro.this, "Todos os campos são obrigatórios", Toast.LENGTH_LONG).show ();
                    }

                    Ion.with(cadastro.this)
                            .load(url)
                            .setBodyParameter("nome_app", nome)
                            .setBodyParameter("email_app", email)
                            .setBodyParameter("senha_app", senha)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    try {
                                        //Toast.makeText(MainCadastro.this, "Nome: " + result.get("NOME").getAsString(), Toast.LENGTH_LONG).show ();
                                        String RETORNO = result.get("CADASTRO").getAsString();

                                        if(RETORNO.equals("EMAIL_ERRO")){
                                            Toast.makeText(cadastro.this, "Ops! Este email já está cadastrado", Toast.LENGTH_LONG).show ();
                                        } else if(RETORNO.equals("SUCESSO")) {
                                            Intent abrelogin = new Intent(cadastro.this, MainActivity.class);
                                            startActivity(abrelogin);
                                            Toast.makeText(cadastro.this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show ();
                                        } else {
                                            Toast.makeText(cadastro.this, "Ops! Ocorreu um erro", Toast.LENGTH_LONG).show ();
                                        }
                                    } catch (Exception erro) {
                                        Toast.makeText(cadastro.this, "Ops! Ocorreu um erro, " + erro, Toast.LENGTH_LONG).show ();
                                    }
                                }
                            });
                }
                else {
                    Toast.makeText(cadastro.this, "Senhas não conferem", Toast.LENGTH_LONG).show ();
                }
            }
        });

        voltar = findViewById(R.id.btnvoltar);
        voltar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                telalogin();
            }

        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void telalogin() {
        Intent intent = new Intent(cadastro.this, MainActivity.class);
        startActivity(intent);
    }
}









