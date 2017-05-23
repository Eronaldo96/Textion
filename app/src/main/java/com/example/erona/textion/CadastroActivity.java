package com.example.erona.textion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btCadastrar;
    private EditText etNome;
    private EditText etEmail;
    private EditText etSenha;
    private EditText etDataNascimento;
    private RadioButton rbMasculino;
    private RadioButton rbFeminino;
    private ImageView Foto;
    private Button btFoto;
    private int CAMERA_REQUEST = 1;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressBar pbLoading;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        btCadastrar = (Button)findViewById(R.id.btCadastrar);
        etNome = (EditText)findViewById(R.id.etNome);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etSenha = (EditText)findViewById(R.id.etSenha);
        etDataNascimento = (EditText)findViewById(R.id.etDataNascimento);
        rbMasculino = (RadioButton)findViewById(R.id.rbMasculino);
        rbFeminino = (RadioButton)findViewById(R.id.rbFeminino);
        Foto = (ImageView)findViewById((R.id.Foto));
        btFoto = (Button)findViewById(R.id.btFoto);
        btCadastrar.setOnClickListener(this);
        btFoto.setOnClickListener(this);
        pbLoading = (ProgressBar)findViewById(R.id.pbLoading);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        /*btCadastrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                //vai para a tela de login
                Intent TelaLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(TelaLogin);
                //     finish();
            }
        });*/

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


    public void onClick (View v){
        mAuth = FirebaseAuth.getInstance();
        switch(v.getId()){

            case R.id.btCadastrar:
                Toast.makeText(CadastroActivity.this, "Clicado em Salvar", Toast.LENGTH_SHORT).show();

                final String nome = etNome.getText().toString();
                final String email = etEmail.getText().toString();
                String senha = etSenha.getText().toString();
                final String data = etDataNascimento.getText().toString();
                String sexo = null;

                if(TextUtils.isEmpty(email) ){
                    Toast.makeText(getApplicationContext(),"Digite o email!" , Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(nome) ){
                    Toast.makeText(getApplicationContext(),"Digite o nome!" , Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(senha) ){
                    Toast.makeText(getApplicationContext(),"Digite a senha!" , Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(data) ){
                    Toast.makeText(getApplicationContext(),"Digite a data!" , Toast.LENGTH_SHORT).show();
                    return;
                }

                pbLoading.setVisibility(View.VISIBLE);

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(rbMasculino.isChecked()){
                    sexo = "Masculino";
                }
                if(rbFeminino.isChecked()){
                    sexo = "Feminino";
                }

                final String finalSexo = sexo;
                mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pbLoading.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(CadastroActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                } else{

                                    Usuario p = new Usuario(email,nome,data, finalSexo,"00");

                                        mDatabaseReference.child("Usuarios").push().setValue(p);

                                    Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });


               // Toast.makeText(CadastroActivity.this, "Dados cadastrados\n"+"Nome = "+nome+"\n"+"Email = "+email+"\n"+"Data de Nascimento = "+data, Toast.LENGTH_SHORT).show();


                break;

            case R.id.btFoto:
                Toast.makeText(CadastroActivity.this, "Clicado em Foto", Toast.LENGTH_SHORT).show();
                Intent telaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(telaCamera,CAMERA_REQUEST);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);
        if(requestCode == CAMERA_REQUEST && resultCode ==RESULT_OK){
            Bitmap photo = (Bitmap)data.getExtras().get("data");
            Foto.setImageBitmap(photo);
        }
    }

}
