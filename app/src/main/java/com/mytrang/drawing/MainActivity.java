package com.mytrang.drawing;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private SimpleDrawingView drawingView;
    private Button btncanvas;
    private TextView name;
    private Kanji kanji;
    private String nameKanji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        drawingView = findViewById(R.id.drawing);
        name = findViewById(R.id.txt_name);

        nameKanji = name.getText().toString().trim();
        kanji = drawingView.getKanji();
        kanji.setName(nameKanji);
        Log.w("List Kanji", kanji.toString());

        btncanvas = findViewById(R.id.buttonClear);
        btncanvas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.clear();
                Log.w("List Kanji", kanji.toString());
                drawingView.clearKanji();
                nameKanji = name.getText().toString().trim();
                kanji = drawingView.getKanji();
                kanji.setName(nameKanji);
            }
        });
    }
}