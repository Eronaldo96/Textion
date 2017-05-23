package com.example.erona.textion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class HomeActivity extends AppCompatActivity {

    private Button btVerLivros;
    private Button btCriarTexto;
    private Button btNovidades;
    private Button btSair;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabaseReference;
    //public SearchView svPesquisar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btVerLivros = (Button)findViewById(R.id.btVerLivros);
        btCriarTexto = (Button)findViewById(R.id.btCriarTexto);
        btNovidades = (Button)findViewById(R.id.btNovidades);
        btSair = (Button)findViewById(R.id.btSair);
        //svPesquisar = (SearchView)findViewById(R.id.svPesquisar);

        btVerLivros.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                //vai para a tela de login
                Intent TelaBiblioteca = new Intent(getApplicationContext(), BibliotecaActivity.class);
                startActivity(TelaBiblioteca);
                //     finish();
            }
        });

        btCriarTexto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                //vai para a tela de login
                Intent TelaCriarTexto = new Intent(getApplicationContext(), EditorTextActivity.class);
                startActivity(TelaCriarTexto);
                //     finish();
            }
        });

        btNovidades.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                //vai para a tela de login
                Intent TelaNovidades = new Intent(getApplicationContext(), PublicacoesActivity.class);
                startActivity(TelaNovidades);
                //     finish();
            }
        });

        btSair.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){

                mAuth.signOut();
                Toast.makeText(getApplicationContext(),"saiu",Toast.LENGTH_SHORT).show();
                //vai para a tela de login
                Intent TelaSair = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(TelaSair);
                finish();
            }
        });

    }

   /* @Override
    protected void onResume() {
        super.onResume();
 /*       Intent TelaHome = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(TelaHome);
        finish();
    }*/
}
