package com.jd.fill.bean;

/**
 * Created by houhuang on 18/3/12.
 */
public class GameItemInfo {
    private int row;
    private int col;
    private int[] state;
    private char[] hint;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int[] getState() {
        return state;
    }

    public void setState(int[] state) {
        this.state = state;
    }

    public char[] getHint() {
        return hint;
    }

    public void setHint(char[] hint) {
        this.hint = hint;
    }
}
