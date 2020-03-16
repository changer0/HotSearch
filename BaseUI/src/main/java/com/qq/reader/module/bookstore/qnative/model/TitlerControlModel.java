package com.qq.reader.module.bookstore.qnative.model;

import java.io.Serializable;

public class TitlerControlModel implements Serializable {
    public boolean needImmerseMode = true;
    public boolean enable = false;
    //for listview
    public int startPosition;
    //for scrollView
    public int startY;
    //controll mode
    public int mode;
    public static int POSITION_MODE = 0;
    public static int Y_MODE = 1;
    public static int POSITION_Y_MODE = 2;

    public TitlerControlModel() {
    }

    public TitlerControlModel(int mode, int startPosition, int startY) {
        this.mode = mode;
        this.startPosition = startPosition;
        this.startY = startY;
    }
}
