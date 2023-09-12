package com.sistempakar.gastritisdiagnosis;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.bluejamesbond.text.style.TextAlignment;


public class Tentang extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);

        tv = (TextView)findViewById(R.id.tv);

        DocumentView dv1 = new DocumentView(this, DocumentView.PLAIN_TEXT);
        dv1.getDocumentLayoutParams().setTextAlignment(TextAlignment.JUSTIFIED);
        DocumentView dv2 = new DocumentView(this, DocumentView.PLAIN_TEXT);
        dv2.getDocumentLayoutParams().setTextAlignment(TextAlignment.JUSTIFIED);
        DocumentView dv3 = new DocumentView(this, DocumentView.PLAIN_TEXT);
        dv3.getDocumentLayoutParams().setTextAlignment(TextAlignment.JUSTIFIED);
    }
}