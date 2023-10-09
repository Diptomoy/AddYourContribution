package battleship;

public class Cell implements Cloneable {
    private boolean isHit;
    private boolean isShipHere;

    public Cell() {
        isHit = false;
        isShipHere = false;
    }

    public Cell(boolean isHit, boolean isShipHere) {
        this.isHit = isHit;
        this.isShipHere = isShipHere;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    public boolean isShipHere() {
        return isShipHere;
    }

    public void setShipHere(boolean shipHere) {
        if (isShipHere) {
            System.out.println("exception!");
            throw new TooCloseException("Error! You placed it too close to another one. Try again:");
        } else {
            isShipHere = shipHere;
        }
    }

    @Override
    public String toString() {
        return isShipHere ? isHit ? "X" : "O" : isHit ? "M" : "~";
    }

    @Override
    protected Cell clone() throws CloneNotSupportedException {
        return (Cell) super.clone();
//        return new Cell(this.isHit, this.isShipHere);
    }
}
