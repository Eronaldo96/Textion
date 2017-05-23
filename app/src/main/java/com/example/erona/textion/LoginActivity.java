package com.example.erona.textion;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cz.msebera.android.httpclient.auth.AuthState;


public class LoginActivity extends AppCompatActivity {
    private Button btLogar;
    private Button btCriarConta;
    private Button btEsqueceuSenha;
    private EditText etEmail;
    private EditText etSenha;
    private ProgressBar pbLoading;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            mAuth = FirebaseAuth.getInstance();

            btLogar = (Button)findViewById(R.id.btLogar);
            btEsqueceuSenha = (Button)findViewById(R.id.btEsqueceuSenha);
            btCriarConta = (Button)findViewById(R.id.btCriarConta);
            etEmail = (EditText)findViewById(R.id.etEmail);
            etSenha = (EditText)findViewById(R.id.etSenha);
            pbLoading = (ProgressBar) findViewById(R.id.pbLoading);


            btLogar.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick (View v){

                    String email = etEmail.getText().toString();
                    String senha = etSenha.getText().toString();

                   if(TextUtils.isEmpty(email) ){
                       Toast.makeText(getApplicationContext(),"Enter email adress!" , Toast.LENGTH_SHORT).show();
                       return;
                   }
                   if(TextUtils.isEmpty(senha)){
                       Toast.makeText(getApplicationContext(),"Enter password!" , Toast.LENGTH_SHORT).show();
                       return;
                   }

                   pbLoading.setVisibility(View.VISIBLE);

                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pbLoading.setVisibility(View.GONE);
                            if(!task.isSuccessful()){
                                if(etSenha.length() < 6){
                                    Toast.makeText(LoginActivity.this, getString(R.string.minimum_password), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                }

                            }else {
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });



                }
            });

            btCriarConta.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick (View v){
                    //vai para a tela de cadastro
                    Intent TelaCriarConta = new Intent(getApplicationContext(), CadastroActivity.class);
                    startActivity(TelaCriarConta);
                   //finish();
                }
            });

            btEsqueceuSenha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent TelaRedefinir = new Intent(getApplicationContext(), RedefinirSenhaActivity.class);
                    startActivity(TelaRedefinir);
                }
            });

            /*mAuthListener = new FirebaseAuth.AuthStateListener(){
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if(user!=null){
                        Log.d(, "onAuthStateChanged:signed_in:" + user.getUid());
                    } else {
                        Log.d(, "onAuthStateChanged:signed_out:");
                    }
                }

            };*/

        }

        /*@Override
        public void onStart(){
            super.onStart();
            mAuth.addAuthStateListener(mAuthListener);
        }

        @Override
        public void onStop(){
            super.onStop();
            if(mAuthListener!=null){
                mAuth.removeAuthStateListener(mAuthListener);
            }
        }*/

    }

