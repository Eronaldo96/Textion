package com.example.erona.textion;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditorTextActivity extends AppCompatActivity {

    private Button btHome;
    private Button btSalvarRascunho;
    private Button btPublicar;
    private EditText etTitulo;
    private EditText etTexto;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDataBaseReference;
    private ProgressBar pbLoading;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_text);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();

        btHome = (Button)findViewById(R.id.btHome);
        btSalvarRascunho = (Button)findViewById(R.id.btSalvar);
        btPublicar = (Button)findViewById(R.id.btPublicar);
        etTexto = (EditText)findViewById(R.id.etTexto);
        etTitulo = (EditText)findViewById(R.id.etTitulo);
        pbLoading = (ProgressBar)findViewById(R.id.pbLoading);

        mDataBaseReference = FirebaseDatabase.getInstance().getReference();

        btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent telaHome = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(telaHome);
            }
        });

        btSalvarRascunho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String texto = etTexto.getText().toString();
                String titulo = etTitulo.getText().toString();

                pbLoading.setVisibility(View.VISIBLE);

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Textos p = new Textos(texto,titulo);
                p.setStatus(0);
                mDataBaseReference.child(mFirebaseUser.getUid()).child("Textos").push().setValue(p);

                Toast.makeText(EditorTextActivity.this, "Clicado em Salvar", Toast.LENGTH_SHORT).show();
                Toast.makeText(EditorTextActivity.this, "Dados salvos\n"+"Titulo = "+titulo+"Texto = "+texto, Toast.LENGTH_SHORT).show();

                Intent telaSalvar = new Intent(getApplicationContext(),BibliotecaActivity.class);
                startActivity(telaSalvar);
            }
        });



        btPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String texto = etTexto.getText().toString();
                String titulo = etTitulo.getText().toString();

                pbLoading.setVisibility(View.VISIBLE);

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Textos p = new Textos(texto,titulo);
                p.setStatus(1);

                mDataBaseReference.child(mFirebaseUser.getUid()).child("Textos").push().setValue(p);

                Toast.makeText(EditorTextActivity.this, "Clicado em Salvar", Toast.LENGTH_SHORT).show();
                Toast.makeText(EditorTextActivity.this, "Dados salvos\n"+"Titulo = "+titulo+"Texto = "+texto, Toast.LENGTH_SHORT).show();

                Intent telaSalvar = new Intent(getApplicationContext(),BibliotecaActivity.class);
                startActivity(telaSalvar);
            }
        });


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            public static final String TAG = "MyActivity";

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /*public void onClick(View v){

        switch (v.getId()){
            case R.id.btSalvar:
                Toast.makeText(EditorTextActivity.this, "Clicado em Salvar", Toast.LENGTH_SHORT).show();
                String texto = etTexto.getText().toString();
                Toast.makeText(EditorTextActivity.this, "Dados salvos\n"+"Texto = "+texto, Toast.LENGTH_SHORT).show();

                break;
        }

    }*/
}
