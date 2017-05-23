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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RedefinirSenhaActivity extends AppCompatActivity {

    private Button btRedefinir;
    private EditText etEmail;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

         mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);

        etEmail = (EditText)findViewById(R.id.etEmail);
        btRedefinir = (Button)findViewById(R.id.btRedefinir);
        pbLoading = (ProgressBar)findViewById(R.id.pbLoading);

        btRedefinir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();

                if(TextUtils.isEmpty(email) ){
                    Toast.makeText(getApplicationContext(),"Enter email adress!" , Toast.LENGTH_SHORT).show();
                    return;
                }

                pbLoading.setVisibility(View.VISIBLE);

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mAuthListener = new FirebaseAuth.AuthStateListener() {
                    public static final String TAG ="Autenticado" ;

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

                //FirebaseAuth auth = FirebaseAuth.getInstance();
                //String emailAddress =  mAuth.getCurrentUser().getEmail() ;//auth.getCurrentUser().getEmail();

                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    public static final String TAG = "Email enviado";

                    @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");
                                }
                            }
                });
                Intent intent = new Intent(RedefinirSenhaActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
