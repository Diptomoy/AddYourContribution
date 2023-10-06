import java.util.Arrays;
import java.util.Random;

import static java.lang.System.out;

public class MineField {
    static int length;
    static int height;
    int[][] field;
    int[][] counts;

    String[][] visibleField;
    int minesFound = 0;
    int totalNumOfMines;

    public MineField(Difficulties level) {
        MineField.length = level.length;
        MineField.height = level.height;
        field = new int[height][length];
        counts = new int[height][length];
        visibleField = new String[height][length];
    }

    void setInitVisibleField() {
        for (String[] strings : visibleField) {
            Arrays.fill(strings, ".");
        }
    }

    void updateVisibleField(int x, int y) {
        int count = counts[x][y];  //get number from the argument
        //if it's surroundings is free of mines,
        if (count == 0) {
            for (int xAdd = -1; xAdd < 2; xAdd++) { //no code suggestion
                for (int yAdd = -1; yAdd < 2; yAdd++) { //no code suggestion
                    int xc = x + xAdd;
                    int yc = y + yAdd;
                    try {
                        if (xAdd == 0 && yAdd == 0) {
                            visibleField[xc][yc] = "/";
                            continue;
                        }
                        if (counts[xc][yc] == 0) {
                            if (visibleField[xc][yc].equals("/")) {
                                continue;
                            }
                            visibleField[xc][yc] = "/";
                            updateVisibleField(xc, yc);
                        } else {
                            visibleField[xc][yc] = String.valueOf(counts[xc][yc]);
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                    }
                }
            }
        } else {
            visibleField[x][y] = String.valueOf(count);
        }
    }

    boolean checkIfAllExplored() {
        for (int i = 0; i < field.length; i++) {
            int[] row = field[i];
            for (int i1 = 0; i1 < row.length; i1++) {
                int point = row[i1];
                if (point == 0) {
                    if (visibleField[i][i1].matches("[.*]")) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    boolean foundAllMines() {
        return totalNumOfMines == minesFound;
    }

    boolean explore(int x, int y, boolean isFirst) {
        if (isFirst) {
            firstMine(x, y);
            updateVisibleField(x, y);
            return false;
        } else if (field[x][y] == 1) {
            return true;
        } else {
            updateVisibleField(x, y);
            return false;
        }
    }

    void mine(int x, int y) {
        if (visibleField[x][y].equals(".")) {
            flag(x, y);
        } else if (visibleField[x][y].equals("*")) {
            unFlag(x, y);
        } else {
            out.println("You are trying to mine a field that is already visible");
        }
    }

    void flag(int x, int y) {
        visibleField[x][y] = "*";
        if (field[x][y] == 1) {
            minesFound++;
        }
    }

    void unFlag(int x, int y) {
        visibleField[x][y] = ".";
        if (field[x][y] == 1) {
            minesFound--;
        }
    }

    void firstMine(int x, int y) {

        while (field[x][y] == 1) {
            int newX = (int) (Math.random() * 2);
            int newY = (int) (Math.random() * 2);
            if (field[newX][newY] != 1) {
                field[x][y] = 0;
                field[(int) (Math.random() * 2)][(int) (Math.random() * 2)] = 1;
            }
        }
        countNumber();
    }

    void createField() {
        switch (length) {
            case 26 -> totalNumOfMines = 78;
            case 20 -> totalNumOfMines = 37;
            case 12 -> totalNumOfMines = 10;
        }
        Random fieldMaker = new Random();
        for (int i = 0; i < totalNumOfMines; i++) {
            int r = fieldMaker.nextInt(height);
            int c = fieldMaker.nextInt(length);

            while (field[r][c] == 1) {
                r = fieldMaker.nextInt(height);
                c = fieldMaker.nextInt(length);
            }
            field[r][c] = 1;
        }
    }

    void countNumber() {
        counts = new int[height][length];
        for (int x = 0; x < field.length; x++) {
            int[] row = field[x];
            for (int y = 0; y < row.length; y++) {
                for (int xAdd = -1; xAdd < 2; xAdd++) {
                    for (int yAdd = -1; yAdd < 2; yAdd++) {
                        if (xAdd == 0 && yAdd == 0) {
                            continue;
                        }
                        try {
                            if (field[x + xAdd][y + yAdd] == 1) { //field is 9x9 2d array
                                counts[x][y]++;
                            }
                        } catch (ArrayIndexOutOfBoundsException ignored) {

                        }
                    }
                }
            }
        }
    }
}
