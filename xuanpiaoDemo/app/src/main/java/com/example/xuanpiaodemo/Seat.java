package com.example.xuanpiaodemo;

public class Seat {
    int type;
    int x;
    int y;
    int i;
    int j;
    public Seat(int type,int i,int j ,int x,int y){
        this.type = type;
        this.x = x;
        this.y = y;
        this.i = i;
        this.j = j;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }
}
