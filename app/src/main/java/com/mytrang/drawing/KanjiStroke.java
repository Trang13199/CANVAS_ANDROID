package com.mytrang.drawing;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

public class KanjiStroke {
    private List<Point> pointList;
    private Point start, end;

    public KanjiStroke() {
        pointList = new ArrayList<>();
    }

    public KanjiStroke(List<Point> pointList) {
        this.pointList = pointList;
    }

    public void addPoint(Point p) {
        pointList.add(p);
    }

    public List<Point> getPointList() {
        return pointList;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public void setPointList(List<Point> pointList) {
        this.pointList = pointList;
    }

    public Point getPoint(int index) {
        return pointList.get(index);
    }

    public Point lastPoint() {
        return pointList.get(pointList.size() - 1);
    }

    public void finalPoint() {
        start = getPoint(0);
        end = lastPoint();
    }

    @Override
    public String toString() {
        return "Kanji{" +
                "pointList=" + pointList +
                ", start=" + start +
                ", end=" + end +
                '}' + "\n";
    }
}
