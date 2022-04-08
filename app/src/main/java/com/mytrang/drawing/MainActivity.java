package com.mytrang.drawing;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
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

        float scale = getResources().getDisplayMetrics().density;
        float scaleText = getResources().getDisplayMetrics().scaledDensity;
//        if (2.5 < scale && scale < 2.75) {
        name.setTextSize(200 * scale / scaleText);
//        } else {
//            name.setTextSize(200);
//        }

        nameKanji = name.getText().toString().trim();
        kanji = drawingView.getKanji();
        kanji.setName(nameKanji);

        btncanvas = findViewById(R.id.buttonClear);
        btncanvas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.clear();
                Log.w("List Kanji main", kanji.toString());
                drawingView.clearKanji();
                nameKanji = name.getText().toString().trim();
                kanji = drawingView.getKanji();
                kanji.setName(nameKanji);
            }
        });
    }

    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }
}