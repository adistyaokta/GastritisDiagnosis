package com.sistempakar.gastritisdiagnosis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class RuleG17 extends AppCompatActivity {

    TextView pertanyaan;
    TextView qgejala, qyakin;
    Button btnYa;
    Button btnTidak;
    Spinner spinner;
    private SQLiteDatabase db;
    private GejalaAdapter dataAdapter = null;
    int globalInc = 0;
    ArrayList<Gejala> gejalaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule_g01);

        DBHelper mDBHelper = new DBHelper(this);
        if (mDBHelper.openDatabase())
            db = mDBHelper.getReadableDatabase();

        qgejala = findViewById(R.id.qgejala); // Assuming you have a TextView with id qgejala
        btnYa = findViewById(R.id.btnYa); // Assuming you have a Button with id btnYa
        btnTidak = findViewById(R.id.btnTidak); // Assuming you have a Button with id btnTidak
        qyakin = findViewById(R.id.qyakin);
        spinner = findViewById(R.id.spinnerQyakin);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_value, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // initialize the list
        gejalaList = new ArrayList<>();
        Gejala gejala;

        // retrieve gejala from the database
        String query = "SELECT nama_gejala, kode_gejala FROM gejala ORDER BY kode_gejala";
        Cursor cursor = db.rawQuery(query, null);

        // loop through the cursor to add gejala to the list
        while (cursor.moveToNext()) {
            gejala = new Gejala(cursor.getString(0), false, 0, cursor.getString(1));
            gejalaList.add(gejala);
        }

        cursor.close();

        // Set the text of qgejala from the first gejala in the list
        if (!gejalaList.isEmpty()) {
            Gejala firstGejala = gejalaList.get(16);
            qgejala.setText("Apakah anda merasakan " + firstGejala.getNamaGejala() + "? (" + firstGejala.getKodeGejala() + ")");
        }

        btnYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                qyakin.setVisibility(View.VISIBLE);

                // Disable the btnYa button
                btnYa.setEnabled(false);

                // Wait for the spinner selection
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        // Get the selected spinner value
                        String selectedValue = spinner.getSelectedItem().toString();

                        if (!selectedValue.equals("Pilih tingkat keyakinan")) {
                            String kodeGejala = gejalaList.get(16).getKodeGejala();

                            // Call the updateCfUser method to update the database
                            DBHelper dbHelper = new DBHelper(RuleG17.this);
                            dbHelper.updateCfUser(kodeGejala, selectedValue);
                            Intent intent = new Intent(RuleG17.this, RuleG18.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // Do nothing if nothing is selected
                    }
                });
            }
        });
        btnTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Gejala> selectedGejalaList = new ArrayList<>();

                for (Gejala gejala : gejalaList) {
                    String kodeGejala = gejala.getKodeGejala();
                    if (kodeGejala.equals("G01") || kodeGejala.equals("G02") || kodeGejala.equals("G03") ||
                            kodeGejala.equals("G04") || kodeGejala.equals("G16")) {
                        selectedGejalaList.add(gejala);
                    }
                }

                // Start HasilKonsul activity and pass the selected gejala list
                Intent intent = new Intent(RuleG17.this, HasilDiagnosa.class);
                intent.putParcelableArrayListExtra("selected_gejala_list", selectedGejalaList);
                startActivity(intent);
            }
        });
    }
}
