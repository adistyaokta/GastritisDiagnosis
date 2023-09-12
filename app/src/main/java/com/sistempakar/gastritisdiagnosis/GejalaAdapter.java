package com.sistempakar.gastritisdiagnosis;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GejalaAdapter extends RecyclerView.Adapter<GejalaAdapter.ViewHolder> {
    ArrayList<Gejala> listGejala;
    final String[] options = {"Tidak - 0", "Tidak Tahu - 0.2", "Sedikit Yakin - 0.4", "Cukup Yakin - 0.6", "Yakin - 0.8", "Sangat Yakin - 1"};

    public GejalaAdapter(ArrayList<Gejala> listGejala) {
        this.listGejala = listGejala;
    }

    public ArrayList<Gejala> getSelectedGejala() {
        return listGejala.stream().filter(Gejala::isSelected).collect(Collectors.toCollection(ArrayList::new));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_gejala, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Gejala gejala = listGejala.get(position);
        String namaGejala = gejala.getKodeGejala() + " - " + gejala.getNamaGejala();

        holder.cbGejala.setChecked(gejala.isSelected());
        holder.cbGejala.setText(namaGejala);
        holder.cbGejala.setOnClickListener((view) -> {
            gejala.setSelected(holder.cbGejala.isChecked());
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(holder.itemView.getContext(),
                android.R.layout.simple_dropdown_item_1line, options);
        holder.spGejala.setAdapter(adapter);
        holder.spGejala.setSelection(getSpinnerIndex(gejala.getCf()));
        holder.spGejala.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                try {
                    float floatValue = getSpinnerValue(selectedItem);

                    if (floatValue == 0) {
                        gejala.setSelected(false);
                        holder.cbGejala.setChecked(false);
                    } else {
                        gejala.setSelected(true);
                        holder.cbGejala.setChecked(true);
                    }

                    gejala.setCf(floatValue);
                    System.out.println("floatValue: " + floatValue);

                } catch (NumberFormatException e) {
                    Log.d("Parsing editable to float", e.toString());
                    gejala.setCf(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                gejala.setCf(0);
            }
        });
    }

    private int getSpinnerIndex(float value) {
        if (value == 0.2f)
            return 1;
        if (value == 0.4f)
            return 2;
        if (value == 0.6f)
            return 3;
        if (value == 0.8f)
            return 4;
        if (value == 1f)
            return 5;

        return 0;
    }

    private float getSpinnerValue(String option) {
        String[] parts = option.split("-");
        String text = parts[1].trim();

        return Float.parseFloat(text);
    }

    @Override
    public int getItemCount() {
        return listGejala.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbGejala;
        Spinner spGejala;

        public ViewHolder(View itemView) {
            super(itemView);
            cbGejala = itemView.findViewById(R.id.check_gejala);
            spGejala = itemView.findViewById(R.id.spinner_gejala);
        }
    }
}