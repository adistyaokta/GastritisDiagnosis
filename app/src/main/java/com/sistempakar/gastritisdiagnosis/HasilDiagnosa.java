package com.sistempakar.gastritisdiagnosis;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class HasilDiagnosa extends AppCompatActivity {
    private SQLiteDatabase db;
    private GejalaAdapter dataAdapter = null;
    int globalInc = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_diagnosa);
        DBHelper mDBHelper = new DBHelper(this);
        if (mDBHelper.openDatabase())
            db = mDBHelper.getReadableDatabase();

        // Retrieve the selected gejala list from the intent
        ArrayList<Gejala> selectedGejalaList = getIntent().getParcelableArrayListExtra("selected_gejala_list");


        // Display the retrieved gejala in a TextView
        TextView textViewHasil = findViewById(R.id.tv_list_gejala_dipilih);
        TextView textViewNamaPenyakit = findViewById(R.id.tv_nama_penyakit);
        TextView textViewPersen = findViewById(R.id.tv_persen);
        StringBuilder hasilBuilder = new StringBuilder();

        Button btn_lihat_daftar_penyakit = findViewById(R.id.btn_lihat_daftar_penyakit);
        btn_lihat_daftar_penyakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HasilDiagnosa.this, DaftarPenyakit.class);
                startActivity(intent);
            }
        });



        Button btn_diagnosa_ulang = findViewById(R.id.btn_diagnosa_ulang);
        btn_diagnosa_ulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HasilDiagnosa.this, RuleG01.class);
                startActivity(intent);
                finish(); // Optional: Call finish to close the current activity if needed
            }
        });


        if (selectedGejalaList != null && !selectedGejalaList.isEmpty()) {
            double hasilCFValue = hasilCF(selectedGejalaList);
            hasilBuilder.append("\n");
            for (Gejala gejala : selectedGejalaList) {
                String namaGejala = gejala.getNamaGejala();
                if (!namaGejala.isEmpty()) {
                    char firstChar = Character.toUpperCase(namaGejala.charAt(0));
                    String restOfString = namaGejala.substring(1);
                    String capitalizedNamaGejala = firstChar + restOfString;
                    hasilBuilder.append(capitalizedNamaGejala)
                            .append(" (").append(gejala.getKodeGejala()).append(")")
                            .append("\n");
                }
            }

        } else {
            hasilBuilder.append("Tidak ada gejala yang dipilih");
        }

        textViewHasil.setText(hasilBuilder.toString());
        textViewPersen.setText("Persentase Kemungkinan " + hasilCF(selectedGejalaList) + " %");

        // Check if the stored gejala matches the specified codes and query the "penyakit" table accordingly
        StringBuilder namaPenyakitBuilder = new StringBuilder();

        if (selectedGejalaList != null) {
            String[] gejalaCodes = getGejalaCodes(selectedGejalaList);

            if (containsCode(gejalaCodes, "G01", "G02", "G03", "G04", "G05", "G20")) {
                // Query the "penyakit" table and retrieve the "nama_penyakit" where kode_penyakit is "P05"
                Cursor cursor = db.rawQuery("SELECT nama_penyakit FROM penyakit WHERE kode_penyakit = 'P05'", null);
                textViewPersen.setVisibility(View.VISIBLE);
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        @SuppressLint("Range") String namaPenyakit = cursor.getString(cursor.getColumnIndex("nama_penyakit"));
                        namaPenyakitBuilder.append("\n").append(namaPenyakit).append("\n");
                        cursor.moveToNext();
                    }
                } else {
                    namaPenyakitBuilder.append("Tidak ada penyakit dengan gejala tersebut");
                }

                cursor.close();
            } else if (containsCode(gejalaCodes, "G01", "G02", "G03", "G04", "G05", "G06", "G07", "G08", "G09")) {
                // Query the "penyakit" table and retrieve the "nama_penyakit" where kode_penyakit is "P04"
                Cursor cursor = db.rawQuery("SELECT nama_penyakit FROM penyakit WHERE kode_penyakit = 'P03'", null);
                textViewPersen.setVisibility(View.VISIBLE);
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        @SuppressLint("Range") String namaPenyakit = cursor.getString(cursor.getColumnIndex("nama_penyakit"));
                        namaPenyakitBuilder.append("\n").append(namaPenyakit).append("\n");
                        cursor.moveToNext();
                    }
                } else {
                    namaPenyakitBuilder.append("Tidak ada penyakit dengan gejala tersebut");
                }

                cursor.close();
            } else if (containsCode(gejalaCodes, "G01", "G02", "G03", "G04", "G16", "G17", "G18", "G19")) {
                // Query the "penyakit" table and retrieve the "nama_penyakit" where kode_penyakit is "P01"
                Cursor cursor = db.rawQuery("SELECT nama_penyakit FROM penyakit WHERE kode_penyakit = 'P01'", null);
                textViewPersen.setVisibility(View.VISIBLE);
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        @SuppressLint("Range") String namaPenyakit = cursor.getString(cursor.getColumnIndex("nama_penyakit"));
                        namaPenyakitBuilder.append("\n").append(namaPenyakit).append("\n");
                        cursor.moveToNext();
                    }
                } else {
                    namaPenyakitBuilder.append("Tidak ada penyakit dengan gejala tersebut");
                }

                cursor.close();
            } else if (containsCode(gejalaCodes, "G01", "G02", "G03", "G04", "G05", "G06", "G07", "G08")) {
                namaPenyakitBuilder.setLength(0);
            } else if (containsCode(gejalaCodes, "G01", "G02", "G03", "G04", "G05", "G06", "G07")) {
                // Query the "penyakit" table and retrieve the "nama_penyakit" where kode_penyakit is "P03"
                Cursor cursor = db.rawQuery("SELECT nama_penyakit FROM penyakit WHERE kode_penyakit = 'P04'", null);
                textViewPersen.setVisibility(View.VISIBLE);
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        @SuppressLint("Range") String namaPenyakit = cursor.getString(cursor.getColumnIndex("nama_penyakit"));
                        namaPenyakitBuilder.append("\n").append(namaPenyakit).append("\n");
                        cursor.moveToNext();
                    }
                } else {
                    namaPenyakitBuilder.append("Tidak ada penyakit dengan gejala tersebut");
                }

                cursor.close();
            } else if (containsCode(gejalaCodes, "G01", "G10", "G11", "G12", "G13", "G14", "G15")) {
                // Query the "penyakit" table and retrieve the "nama_penyakit" where kode_penyakit is "P02"
                Cursor cursor = db.rawQuery("SELECT nama_penyakit FROM penyakit WHERE kode_penyakit = 'P02'", null);
                textViewPersen.setVisibility(View.VISIBLE);
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        @SuppressLint("Range") String namaPenyakit = cursor.getString(cursor.getColumnIndex("nama_penyakit"));
                        namaPenyakitBuilder.append("\n").append(namaPenyakit).append("\n");
                        cursor.moveToNext();
                    }
                } else {
                    namaPenyakitBuilder.append("Tidak ada penyakit dengan gejala tersebut");
                }

                cursor.close();
            }
        }

        if (namaPenyakitBuilder.length() > 0) {
            textViewNamaPenyakit.setText(namaPenyakitBuilder.toString());
        } else {
            textViewNamaPenyakit.setText("Tidak ditemukan penyakit dengan gejala tersebut, mohon menghubungi dokter untuk pemeriksaan lebih lanjut.");
        }


        // Calculate certainty factor
        double certaintyFactor = 1.0;
        if (selectedGejalaList != null && !selectedGejalaList.isEmpty()) {
            for (Gejala gejala : selectedGejalaList) {
                double nilaiCF = getNilaiCF(gejala.getKodeGejala());
                certaintyFactor = certaintyFactor * (1 - nilaiCF);
            }
        }
    }

    private boolean containsCode(String[] codes, String... targetCodes) {
        for (String targetCode : targetCodes) {
            boolean found = false;
            for (String code : codes) {
                if (code.equals(targetCode)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    private String[] getGejalaCodes(ArrayList<Gejala> gejalaList) {
        String[] codes = new String[gejalaList.size()];
        for (int i = 0; i < gejalaList.size(); i++) {
            codes[i] = gejalaList.get(i).getKodeGejala();
        }
        return codes;
    }


    @SuppressLint("Range")
    private double getNilaiCF(String kodeGejala) {
        double nilaiCF = 0;
        Cursor cursor = db.rawQuery("SELECT nilai_cf FROM rule WHERE kode_gejala = ?", new String[]{kodeGejala});
        if (cursor.moveToFirst()) {
            nilaiCF = cursor.getDouble(cursor.getColumnIndex("nilai_cf"));
        }
        cursor.close();
        return nilaiCF;
    }
    @SuppressLint("Range")
    private double getCfUser(String kodeGejala) {
        double CFUser = 0;
        Cursor cursor = db.rawQuery("SELECT cf_user FROM rule WHERE kode_gejala = ?", new String[]{kodeGejala});
        if (cursor.moveToFirst()) {
            CFUser = cursor.getDouble(cursor.getColumnIndex("cf_user"));
        }
        cursor.close();
        return CFUser;
    }

    @SuppressLint("Range")
    private double combinedCF(String kodeGejala) {
        double nilaiCF = getNilaiCF(kodeGejala);
        double CFUser = getCfUser(kodeGejala);
        double result = nilaiCF * CFUser;

        result = Math.round(result * 10000.0) / 10000.0;

        return result;
    }

    private double hasilCF(ArrayList<Gejala> selectedGejalaList) {
        if (selectedGejalaList == null || selectedGejalaList.isEmpty()) {
            return 0.0; // Return 0 if there are no selected gejala.
        }

        double hasilCF = combinedCF(selectedGejalaList.get(0).getKodeGejala());

        for (int i = 1; i < selectedGejalaList.size(); i++) {
            double CF2 = combinedCF(selectedGejalaList.get(i).getKodeGejala());
            hasilCF = hasilCF + CF2 * (1 - hasilCF);
        }

        hasilCF = Math.round(hasilCF * 10000.0) / 100.0;

        return hasilCF;
    }



}
