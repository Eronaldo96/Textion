package com.example.erona.textion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PublicacoesActivity extends AppCompatActivity {
    public Button btHome;
    private ListView lvLista;

    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblioteca);

        btHome = (Button) findViewById(R.id.btHome);
        lvLista = (ListView) findViewById(R.id.lvLista);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();

        final ArrayAdapter<Textos> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);

        lvLista.setAdapter(adapter);


        mDatabase.child(mFirebaseUser.getUid()).child("Textos").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Textos t = dataSnapshot.getValue(Textos.class);
                if (t.getStatus() == 1)
                    adapter.add(t);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // adapter.remove((String) dataSnapshot.child("placa").getValue());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String titulo = ((TextView) view).getText().toString();

                Object o = lvLista.getAdapter().getItem(position);

                Textos t = (Textos) o;


                Toast.makeText(getApplicationContext(), "Titulo clicado " + titulo, Toast.LENGTH_LONG).show();

                Intent i = new Intent(getApplicationContext(), TextoActivity.class);
                i.putExtra("texto", t);
                startActivity(i);

            }
        });

        btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent telaHome = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(telaHome);
            }
        });
    }
}
