package com.example.fierydragon.Grounds;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class Volcano {
    @Expose
    private String id;
    @Expose
    private ArrayList<Cell> cells;
    @Expose
    private int VolcanoNumber;

    public Volcano(int id) {
        this.id = "Volcano" + id;
        this.cells = new ArrayList<>();
        this.VolcanoNumber = id;
    }

    public void addCell(Cell cell) {
        this.cells.add(cell);
    }

    public ArrayList<Cell> getCells() {
        return this.cells;
    }

    public int getCellCount() {
        return this.cells.size();
    }

    public String getId() {
        return this.id;
    }
    public int getVolcanoNumber() {
        return VolcanoNumber;
    }

    public void setVolcanoNumber(int volcanoNumber) {
        VolcanoNumber = volcanoNumber;
    }
}