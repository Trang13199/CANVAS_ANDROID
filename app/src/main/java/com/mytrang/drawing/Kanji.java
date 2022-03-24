package com.mytrang.drawing;

import java.util.ArrayList;
import java.util.List;

public class Kanji {
    private List<KanjiStroke> kanjiStrokes;
    private String name;

    public Kanji() {
        kanjiStrokes = new ArrayList<>();
    }

    public void addKanji(KanjiStroke stroke) {
        kanjiStrokes.add(stroke);
    }

    public List<KanjiStroke> getKanjiStrokes() {
        return kanjiStrokes;
    }

    public void setKanjiStrokes(List<KanjiStroke> kanjiStrokes) {
        this.kanjiStrokes = kanjiStrokes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return kanjiStrokes +
                ", name='" + name + '\'' + "\n";

    }
}
