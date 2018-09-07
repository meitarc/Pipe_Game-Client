package sample.model;

import java.util.*;

public class PGBModel extends Observable {

    char[][] result;
    public PGBModel()
    {
        result= new char[][]{
                {'s', '-', '7', ' '},
                {' ', '|', 'L', '7'},
                {'-', 'F', ' ', '|'},
                {'7', 'F', '-', 'J'},
                {' ', 'g', ' ', '-'}};
    }

    public void switchCell(int i, int j, int times) {

        for(int t=0;t<times;t++) {
            switch (this.result[i][j]) {
                case '-': {
                    this.result[i][j] = '|';
                    break;
                }
                case '|': {
                    this.result[i][j] = '-';
                    break;
                }
                case '7': {
                    this.result[i][j] = 'J';
                    break;
                }
                case 'J': {
                    this.result[i][j] = 'L';
                    break;
                }
                case 'L': {
                    this.result[i][j] = 'F';
                    break;
                }
                case 'F': {
                    this.result[i][j] = '7';
                }
            }
        }
        setChanged();
        notifyObservers();
    }
    public char[][] getResult() {
        return result;
    }
    public void setData(char[][] data)
    {
        this.result= data;
        setChanged();
        notifyObservers();
    }
}
