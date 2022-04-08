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
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

public class SimpleDrawingView extends View {
    private final int paintColor = Color.GREEN;

    private Paint drawPaint, canvasPaint;

    private Bitmap bitmap;

    private Bitmap redDot, greenDot;
    private int x1, x2, y1, y2;
    private int check = 0;
    private int i = 0;
    private KanjiStroke kanjiStroke;
    private Kanji kanji;
    private Point firstPoint, lastPoint;

    private Point start, end;
    private KanjiStroke ks;
    private Kanji kj;

    private Canvas canvas;
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
        kanjiStroke = new KanjiStroke();
        kanji = new Kanji();
        kj = new Kanji();
        kj = getListKanji();

        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(20f);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);


        ks = kj.getKanjiStrokes().get(i);
        start = ks.getPointList().get(0);
        end = ks.getPointList().get(1);

        x1 = convertdptopx(start.x);
        y1 = convertdptopx(start.y);
        x2 = convertdptopx(end.x);
        y2 = convertdptopx(end.y);

        redDot = Bitmap.createScaledBitmap(drawableToBitmap(R.drawable.red_dot), convertdptopx(10), convertdptopx(10), false);
        greenDot = Bitmap.createScaledBitmap(drawableToBitmap(R.drawable.green_dot), convertdptopx(10), convertdptopx(10), false);

    }

    private int convertdptopx(int input) {
        float scale = getResources().getDisplayMetrics().density;
        return Math.round(input * scale);
    }

    private int convertpxtodp(int input) {
        float scale = getResources().getDisplayMetrics().density;
        return Math.round(input / scale);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(greenDot, x1, y1, null);
        canvas.drawBitmap(redDot, x2, y2, null);
//
//        canvas.drawLine(0, 0, getWidth(), getHeight(), drawPaint);
//        canvas.drawLine(getWidth(), 0, 0, getHeight(), drawPaint);
//        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), drawPaint);
//        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, drawPaint);


//        canvas.drawLine(convertdptopx(195), convertdptopx(245), convertdptopx(195), convertdptopx(155), drawPaint);
//        canvas.drawLine(convertdptopx(245), convertdptopx(195), convertdptopx(155), convertdptopx(195), drawPaint);

//        canvas.drawBitmap(redDot, convertdptopx(192-5), convertdptopx(177-5), null);
//        canvas.drawBitmap(redDot, convertdptopx(248-5), convertdptopx(176-5), null);
//        canvas.drawBitmap(redDot, convertdptopx(230), convertdptopx(127), null);
//        canvas.drawBitmap(redDot, convertdptopx(158), convertdptopx(128), null);
//        canvas.drawBitmap(redDot, convertdptopx(271), convertdptopx(195), null);
//        canvas.drawBitmap(redDot, convertdptopx(267), convertdptopx(220), null);
//        canvas.drawBitmap(redDot, convertdptopx(195), convertdptopx(228), null);
//        canvas.drawBitmap(redDot, convertdptopx(195), convertdptopx(245), null);
//        canvas.drawBitmap(redDot, convertdptopx(142), convertdptopx(245), null);


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
            Log.w("OK", "x1: " + convertpxtodp(x1) + " - y1: " + convertpxtodp(y1) + " - x2: " + convertpxtodp(x2) + " - y2: " + convertpxtodp(y2));
        } else {
            check = 0;
            Log.w("NO", "x1: " + convertpxtodp(x1) + " - y1: " + convertpxtodp(y1) + " - x2: " + convertpxtodp(x2) + " - y2: " + convertpxtodp(y2));
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
            if (i < kj.getKanjiStrokes().size()) {
                ks = kj.getKanjiStrokes().get(i);
                start = ks.getPointList().get(0);
                end = ks.getPointList().get(1);

                x1 = convertdptopx(start.x);
                y1 = convertdptopx(start.y);
                x2 = convertdptopx(end.x);
                y2 = convertdptopx(end.y);
            } else {
            }
            check = 0;
            Log.w("OK", "x1: " + convertpxtodp(x1) + " - y1: " + convertpxtodp(y1) + " - x2: " + convertpxtodp(x2) + " - y2: " + convertpxtodp(y2));
        } else {
            path.reset();
            Log.w("NO", "x1: " + convertpxtodp(x1) + " - y1: " + convertpxtodp(y1) + " - x2: " + convertpxtodp(x2) + " - y2: " + convertpxtodp(y2));
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
                firstPoint = new Point(convertpxtodp((int) X - 10), convertpxtodp((int) Y - 10));
                Log.e("start", "x:=" + convertpxtodp((int) X - 10) + " y:= " + convertpxtodp((int) Y - 10));
                Log.d("density", getResources().getDisplayMetrics().scaledDensity + "");
                Log.w("Scale", getResources().getDisplayMetrics().density + " ");
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(X, Y);
                break;
            case MotionEvent.ACTION_UP:
                upTouch(X, Y);
                lastPoint = new Point(convertpxtodp((int) X - 10), convertpxtodp((int) Y - 10));
                if (firstPoint.x == lastPoint.x && firstPoint.y == lastPoint.y) {
//                    Log.e("List Fail", " " + kanji.toString());
//                    Log.w("size", String.valueOf(kanji.getKanjiStrokes().size()));
                    Toast.makeText(this.getContext(), "Please try again!!!", Toast.LENGTH_SHORT).show();
                } else {
                    kanjiStroke.addPoint(firstPoint);
                    kanjiStroke.addPoint(lastPoint);
                    kanjiStroke.finalPoint();

                    for (Point p : kanjiStroke.getPointList()) {
                        Log.e("point", p.toString());
                    }
                    kanji.addKanji(kanjiStroke);
                    Toast.makeText(this.getContext(), "Successful!!!", Toast.LENGTH_SHORT).show();

//                    Log.d("List Success", " " + kanji.toString());
//                    Log.w("size", String.valueOf(kanji.getKanjiStrokes().size()));
                    kanjiStroke = new KanjiStroke();

                }
                canvas.drawPath(path, drawPaint);
                path.reset();
                Log.e("end", "x:=" + convertpxtodp((int) X - 10) + " y:= " + convertpxtodp((int) Y - 10));
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

        x1 = convertdptopx(start.x);
        y1 = convertdptopx(start.y);
        x2 = convertdptopx(end.x);
        y2 = convertdptopx(end.y);
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

    public Kanji getListKanji() {
        KanjiStroke k = new KanjiStroke();

        k.addPoint(new Point(146, 139));
        k.addPoint(new Point(226, 131));

        KanjiStroke k1 = new KanjiStroke();
        k1.addPoint(new Point(121, 195));
        k1.addPoint(new Point(263, 184));

        KanjiStroke k2 = new KanjiStroke();
        k2.addPoint(new Point(184, 147));
        k2.addPoint(new Point(246, 250));

        Kanji j = new Kanji();
        j.addKanji(k);
        j.addKanji(k1);
        j.addKanji(k2);

        return j;
    }
}
