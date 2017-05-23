package com.example.erona.textion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TextoActivity extends AppCompatActivity {

    private TextView tvLeitura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texto);

        tvLeitura = (TextView)findViewById(R.id.tvLeitura);



        Intent i = getIntent();
        //Textos t = i.getExtras();
        Bundle extra = this.getIntent().getExtras();
        Textos t = (Textos)getIntent().getSerializableExtra("texto");

        tvLeitura.setText(t.getTexto());
    }
}
