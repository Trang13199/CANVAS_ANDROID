package com.mytrang.drawing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;

public class SimpleDrawingView extends View {
    private final int paintColor = Color.GREEN;

    private Paint drawPaint, canvasPaint;

    private Bitmap bitmap;

    private Bitmap redDot, greenDot;
    private int x1, x2, y1, y2;
    private Kanji kj;
    private int check = 0;
    private int i = 0;
    private float scale;
    private KanjiStroke kanjiStroke;
    private Kanji kanji;
    private Point start, end;
    private KanjiStroke ks;

    private Canvas canvas;
    private List<Points> mpoint;

    private Path path;

    public SimpleDrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();

    }

    private void setupPaint() {
        drawPaint = new Paint();
        path = new Path();
        mpoint = getListPoint();
        kanjiStroke = new KanjiStroke();
        kanji = new Kanji();
        kj = new Kanji();
        kj = getListKanji();

        // set màu cho nét vẽ
        drawPaint.setColor(paintColor);

        drawPaint.setAntiAlias(true);

        //set giá trị độ rộng của nét vẽ
        drawPaint.setStrokeWidth(20);

        // set kiểu cho nét vẽ
        // FILL: dùng để tô đối tượng,
        // STROKE: dùng để vẽ đường
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);

        ///set stype ở những điêm kết thúc của 2 đường thẳng và có giá trị sau
        /// ROUND: bo tròn nét vẽ ở 2 đầu mút của đoạn thẳng
        //SQUARE: nét vẽ bình thường, sắc cạnh
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);

        scale = getResources().getDisplayMetrics().density; //This scale value is proportional to device, and it will handle your problem.


        ks = kj.getKanjiStrokes().get(i);
        start = ks.getPointList().get(0);
        end = ks.getPointList().get(1);

        x1 = (int) (start.x * scale);
        y1 = (int) (start.y * scale);
        x2 = (int) (end.x * scale);
        y2 = (int) (end.y * scale);


//        Points p = mpoint.get(i);
//        x1 = (int) (p.getX1() * scale);
//        y1 = (int) (p.getY1() * scale);
//        x2 = (int) (p.getX2() * scale);
//        y2 = (int) (p.getY2() * scale);

        redDot = Bitmap.createScaledBitmap(drawableToBitmap(R.drawable.red_dot), 30, 30, false);
        greenDot = Bitmap.createScaledBitmap(drawableToBitmap(R.drawable.green_dot), 30, 30, false);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(greenDot, x1, y1, null);
        canvas.drawBitmap(redDot, x2, y2, null);

        canvas.drawBitmap(bitmap, 0, 0, canvasPaint);
        canvas.drawPath(path, drawPaint);

    }

    // when ACTION_DOWN start touch according to the x,y values
    private void startTouch(float x, float y) {
        path.moveTo(x, y);
        float a = Math.abs(x - x1);
        float b = Math.abs(y - y1);
        if (a < 30 && b < 30) {
            check = 1;
            Log.w("OK", "x1: " + x1 / scale + " - y1: " + y1 / scale + " - x2: " + x2 / scale + " - y2: " + y2 / scale);
        } else {
            check = 0;
            Log.w("NO", "x1: " + x1 / scale + " - y1: " + y1 / scale + " - x2: " + x2 / scale + " - y2: " + y2 / scale);
        }
        Log.d("check", " " + check + "");
    }

    // when ACTION_UP stop touch
    public void upTouch(float x, float y) {
        path.lineTo(x, y);
        float a = Math.abs(x - x2);
        float b = Math.abs(y - y2);
        if (a < 30 && b < 30 && check == 1) {
            i++;
//            if (i < mpoint.size()) {
            if (i < ks.getPointList().size()) {
                ks = kj.getKanjiStrokes().get(i);
                start = ks.getPointList().get(0);
                end = ks.getPointList().get(1);

                x1 = (int) (start.x * scale);
                y1 = (int) (start.y * scale);
                x2 = (int) (end.x * scale);
                y2 = (int) (end.y * scale);
//                Points p = mpoint.get(i);
//                x1 = (int) (p.getX1() * scale);
//                y1 = (int) (p.getY1() * scale);
//                x2 = (int) (p.getX2() * scale);
//                y2 = (int) (p.getY2() * scale);
                Log.d("Scale", " " + scale);
            } else {
            }
            Log.d("Scale", " " + scale);
            check = 0;
            Log.w("OK", "x1: " + x1 / scale + " - y1: " + y1 / scale + " - x2: " + x2 / scale + " - y2: " + y2 / scale);
        } else {
            path.reset();
            Log.d("Scale", " " + scale);
            Log.w("NO", "x1: " + x1 / scale + " - y1: " + y1 / scale + " - x2: " + x2 / scale + " - y2: " + y2 / scale);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float X = event.getX();
        float Y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startTouch(X, Y);
                kanjiStroke.addPoint(new Point((int) (X / scale), (int) (Y / scale)));
                Log.e("start", "x:=" + X / scale + " y: " + Y / scale);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(X, Y);
                break;
            case MotionEvent.ACTION_UP:
                upTouch(X, Y);
                kanjiStroke.addPoint(new Point((int) (X / scale), (int) (Y / scale)));
                kanjiStroke.finalPoint();
                for (Point p : kanjiStroke.getPointList()) {
                    Log.e("point", p.toString());
                }

                Log.e("Stroke", kanjiStroke.toString());
                kanji.addKanji(kanjiStroke);

                Log.d("List Stroke", " " + kanji.toString());
                Log.w("size", String.valueOf(kanji.getKanjiStrokes().size()));
                kanjiStroke = new KanjiStroke();

                canvas.drawPath(path, drawPaint);
                path.reset();
                Log.e("end", "x:=" + X / scale + " y: " + Y / scale);
            default:
                return false;
        }

        invalidate();
        return true;
    }

    public void clear() {
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        i = 0;
        ks = kj.getKanjiStrokes().get(i);
        start = ks.getPointList().get(0);
        end = ks.getPointList().get(1);

        x1 = (int) (start.x * scale);
        y1 = (int) (start.y * scale);
        x2 = (int) (end.x * scale);
        y2 = (int) (end.y * scale);
//        Points p = mpoint.get(i);
//        x1 = (int) (p.getX1() * scale);
//        y1 = (int) (p.getY1() * scale);
//        x2 = (int) (p.getX2() * scale);
//        y2 = (int) (p.getY2() * scale);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    public Bitmap drawableToBitmap(@DrawableRes int drawableID) {

        Drawable d = ResourcesCompat.getDrawable(getResources(), drawableID, null);

        if (d instanceof BitmapDrawable) {
            return ((BitmapDrawable) d).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        d.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        d.draw(canvas);

        return bitmap;
    }

    public Kanji getKanji() {
        return kanji;
    }

    public void clearKanji() {
        kanji = new Kanji();
    }

    public KanjiStroke getKanjiStroke() {
        return kanjiStroke;
    }

    public List<Points> getListPoint() {
        mpoint = new ArrayList<>();


        //      1  Points p1 = new Points(120, 190, 270, 190);


//  2      Points p1 = new Points(130, 150, 250, 150);
//   2     Points p2 = new Points(120, 250, 270, 250);

//        Points p1 = new Points(135, 115, 135, 295);
//        Points p2 = new Points(135, 115, 250, 295);

        Points p1 = new Points(110, 190, 280, 190);
        Points p2 = new Points(190, 110, 190, 280);


        Points p3 = new Points(135, 210, 250, 210);
        Points p4 = new Points(135, 290, 250, 290);
        mpoint.add(p1);
        mpoint.add(p2);
        mpoint.add(p3);
        mpoint.add(p4);

        return mpoint;
    }

    public Kanji getListKanji() {
        KanjiStroke k = new KanjiStroke();
        k.addPoint(new Point(110, 190));
        k.addPoint(new Point(280, 190));

        KanjiStroke k1 = new KanjiStroke();
        k1.addPoint(new Point(190, 110));
        k1.addPoint(new Point(190, 280));

        Kanji j = new Kanji();
        j.addKanji(k);
        j.addKanji(k1);
        return j;
    }
}