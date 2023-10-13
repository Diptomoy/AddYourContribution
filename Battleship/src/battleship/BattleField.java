package battleship;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class BattleField {
    private Cell[][] cells;
    private static final int aircraftCarrierSize = 5;
    private static final int battleshipSize = 4;
    private static final int submarineSize = 3;
    private static final int cruiserSize = 3;
    private static final int destroyerSize = 2;

    public static final int length = 10;
    public static final int height = 10;

    private BattleState battleState;

    private boolean sankAllShips;

    private Map<String, Point[]> shipLocations;

    public BattleField() {
        cells = new Cell[10][10];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell();
            }
        }
        shipLocations = new LinkedHashMap<>();
        sankAllShips = false;
    }

    public void addShip(String start, String end, int sizeLimit, String shipName) {
        sizeLimit--;
        Cell[][] newCells = new Cell[length][height];
        for (int i = 0; i < cells.length; i++) {
            Cell[] row = cells[i];
            for (int j = 0; j < row.length; j++) {
                Cell cell = row[j];
                try {
                    newCells[i][j] = cell.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }

        int x1 = start.charAt(0) - 65;
        int y1 = Integer.parseInt(start.substring(1)) - 1;

        int x2 = end.charAt(0) - 65;
        int y2 = Integer.parseInt(end.substring(1)) - 1;

        boolean isSet = false;
        boolean sizeCondition;
        Point[] points = new Point[sizeLimit + 1];
        java.util.List<Point> pointList = new ArrayList<>();
        if (x1 == x2) {
            int size = Math.abs(y2 - y1);
            sizeCondition = size == sizeLimit;
            if (sizeCondition) {
                while (y2 >= y1) {
                    if (checkIfShipIsNotNear(x1, y1)) {
//                        System.out.println("adding");
                        pointList.add(new Point(x1, y1));
                        newCells[x1][y1++].setShipHere(true);
                        isSet = true;
                    } else {
                        System.out.println(cells[x1][y1 - 1].isShipHere());
                        System.out.println(newCells[x1][y1 - 1].isShipHere());
                        throw new TooCloseException("Error! You placed it too close to another one. Try again:");
                    }
                }
                while (!isSet && y2 <= y1) {
                    if (checkIfShipIsNotNear(x1, y2)) {
//                        System.out.println("adding");
                        pointList.add(new Point(x1, y2));
                        newCells[x1][y2++].setShipHere(true);
                    } else {
                        System.out.println(cells[x1][y2 - 1].isShipHere());
                        throw new TooCloseException("Error! You placed it too close to another one. Try again:");
                    }
                }
            } else {
                throw new SizeException(String.format("Error! Wrong length of the %s! Try again:",
                        shipName));
            }
        } else if (y1 == y2) {
            int size = Math.abs(x2 - x1);
            sizeCondition = size == sizeLimit;
            if (sizeCondition) {
                while (x2 >= x1) {
                    if (checkIfShipIsNotNear(x1, y1)) {
//                        System.out.println("adding");
                        pointList.add(new Point(x1, y1));
                        newCells[x1++][y1].setShipHere(true);
                        isSet = true;
                    } else {
                        throw new TooCloseException("Error! You placed it too close to another one. Try again:");
                    }
                }
                while (!isSet && x2 <= x1) {
                    if (checkIfShipIsNotNear(x2, y1)) {
//                        System.out.println("adding");
                        pointList.add(new Point(x2, y1));
                        newCells[x2++][y1].setShipHere(true);
                    } else {
                        throw new TooCloseException("Error! You placed it too close to another one. Try again:");
                    }
                }
            } else {
                throw new SizeException(String.format("Error! Wrong length of the %s! Try again:",
                        shipName));
            }
        } else {
            throw new PositionException("Error! Wrong ship location! Try again:");
        }
        cells = Arrays.copyOf(newCells, newCells.length);
        pointList.toArray(points);
        shipLocations.put(shipName, points);
    }

    private boolean checkIfShipIsNotNear(int x, int y) {
        try {
            return !cells[x][y - 1].isShipHere() && !cells[x][y + 1].isShipHere() &&
                    !cells[x - 1][y].isShipHere() && !cells[x + 1][y].isShipHere() &&
                    !cells[x - 1][y - 1].isShipHere() && !cells[x + 1][y + 1].isShipHere() &&
                    !cells[x - 1][y + 1].isShipHere() && !cells[x + 1][y - 1].isShipHere();
        } catch (ArrayIndexOutOfBoundsException indexOutOfBoundsException) {
            return true;
        }
    }


    public boolean addAircraftCarrier(String start, String end) {
        try {
            addShip(start, end, aircraftCarrierSize, "Aircraft Carrier");
            return true;
        } catch (PositionException | TooCloseException | SizeException battleShipException) {
            System.out.println();
            System.out.println(battleShipException.getMessage());
            System.out.println();
            return false;
        }
    }

    public boolean addBattleShip(String start, String end) {
        try {
            addShip(start, end, battleshipSize, "BattleShip");
            return true;
        } catch (PositionException | TooCloseException | SizeException battleShipException) {
            System.out.println();
            System.out.println(battleShipException.getMessage());
            System.out.println();
            return false;
        }

    }

    public boolean addSubmarine(String start, String end) {
        try {
            addShip(start, end, submarineSize, "Submarine");
            return true;
        } catch (PositionException | TooCloseException | SizeException battleShipException) {
            System.out.println();
            System.out.println(battleShipException.getMessage());
            System.out.println();
            return false;
        }
    }

    public boolean addCruiser(String start, String end) {
        try {
            addShip(start, end, cruiserSize, "Cruiser");
            return true;
        } catch (PositionException | TooCloseException | SizeException battleShipException) {
            System.out.println();
            System.out.println(battleShipException.getMessage());
            System.out.println();
            return false;
        }
    }

    public boolean addDestroyer(String start, String end) {
        try {
            addShip(start, end, destroyerSize, "Destroyer");
            return true;
        } catch (PositionException | TooCloseException | SizeException battleShipException) {
            System.out.println();
            System.out.println(battleShipException.getMessage());
            System.out.println();
            return false;
        }
    }

    public boolean attackCell(int x, int y) {
        try {
            cells[x][y].setHit(true);
            if (cells[x][y].isShipHere()) {
                final boolean[] hasSunkShip = {false};
                final String[] shipName = new String[1];
                shipLocations.forEach((s, points) -> {
                    if (!hasSunkShip[0]) {
                        hasSunkShip[0] = Arrays.stream(points)
                                .allMatch(point -> cells[point.x][point.y].isHit());
                        if (hasSunkShip[0]) {
                            shipName[0] = s;
                        }
                    }
                });

                if (hasSunkShip[0]) {
                    shipLocations.remove(shipName[0]);
                    if (shipLocations.isEmpty()) {
                        sankAllShips = true;
                        System.out.println("You sank the last ship. You won. Congratulations!");
                    } else {
                        System.out.println("You sank a ship!");
                    }
                } else {
                    System.out.println("You hit a ship!");
                }
            } else {
                System.out.println("You missed.");
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean attackCell(String position) {
        int x = position.charAt(0) - 65;
        int y = Integer.parseInt(position.substring(1)) - 1;
        return attackCell(x, y);
    }

    public BattleState getBattleState() {
        return battleState;
    }

    public void setBattleState(BattleState battleState) {
        this.battleState = battleState;
    }

    public boolean notSankAllShips() {
        return !sankAllShips;
    }

    public void setSankAllShips(boolean sankAllShips) {
        this.sankAllShips = sankAllShips;
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder("  1 2 3 4 5 6 7 8 9 10");
        char c = 'A';
        for (Cell[] row : cells) {
            resultString.append("\n").append(c++);
            for (Cell thisCell : row) {
                resultString.append(" ").append(thisCell.toString());
            }
        }
        if (battleState == BattleState.INVISIBLE) {
            return String.valueOf(resultString).replaceAll("O", "~");
        } else {
            return String.valueOf(resultString);
        }
    }
}
