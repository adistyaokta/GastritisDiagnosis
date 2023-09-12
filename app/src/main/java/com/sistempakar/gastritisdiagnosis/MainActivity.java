package com.sistempakar.gastritisdiagnosis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnDiagnosa, btnPenyakit, btnTentang, btnKeluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDiagnosa = (Button)findViewById(R.id.buttonDiagnosa);
        btnDiagnosa.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, RuleG01.class)));

        btnPenyakit = (Button)findViewById(R.id.buttonPenyakit);
        btnPenyakit.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, DaftarPenyakit.class)));

        btnTentang = (Button)findViewById(R.id.buttonTentang);
        btnTentang.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, Tentang.class)));

        btnKeluar = (Button)findViewById(R.id.buttonKeluar);
        btnKeluar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }
}

