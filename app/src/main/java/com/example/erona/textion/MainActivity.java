package com.example.erona.textion;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler h = new Handler();
        h.postDelayed(this,3000);
    }

    @Override
    public void run() {
        Intent telaPrincipal = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(telaPrincipal);
        finish();
    }
}
