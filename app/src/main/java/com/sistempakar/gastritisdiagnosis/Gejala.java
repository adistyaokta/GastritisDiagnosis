package com.sistempakar.gastritisdiagnosis;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Gejala implements Parcelable {

    private String namaGejala; //nama gejala
    private boolean selected; //gejala diceklis / tidak
    private float cf;
    private String kodeGejala;

    public Gejala(String namaGejala, boolean selected, float cf, String kodeGejala) {
        super();
        this.namaGejala = namaGejala;
        this.selected = selected;
        this.cf = cf;
        this.kodeGejala = kodeGejala;
    }

    protected Gejala(Parcel in) {
        namaGejala = in.readString();
        selected = in.readByte() != 0;
        cf = in.readFloat();
        kodeGejala = in.readString();
    }

    public static final Creator<Gejala> CREATOR = new Creator<Gejala>() {
        @Override
        public Gejala createFromParcel(Parcel in) {
            return new Gejala(in);
        }

        @Override
        public Gejala[] newArray(int size) {
            return new Gejala[size];
        }
    };

    public Gejala(String string, boolean b, int i) {
    }

    public String getNamaGejala() {
        return namaGejala;
    }

    public void setNamaGejala(String namaGejala) {
        this.namaGejala = namaGejala;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public float getCf() {
        return cf;
    }

    public void setCf(float cf) {
        this.cf = cf;
    }

    public void setKodeGejala(String kodeGejala) {
        this.kodeGejala = kodeGejala;
    }

    public String getKodeGejala() {
        return this.kodeGejala;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(namaGejala);
        parcel.writeByte((byte) (selected ? 1 : 0));
        parcel.writeFloat(cf);
        parcel.writeString(kodeGejala);
    }
}
