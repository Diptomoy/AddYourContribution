public class ThreadUpdater extends Thread {
    MineField mineField;
    GameWindow window;
    boolean isStopped = false;

    public ThreadUpdater(MineField mineField, GameWindow window) {
        this.mineField = mineField;
        this.window = window;
    }

    @Override
    public void run() {
        if (!isStopped) {
            for (int x = 0; x < MineField.height; x++) {
                for (int y = 0; y < MineField.length; y++) {
                    try {
                        if (mineField.visibleField[x][y].matches("\\d")) {
                            window.fieldPanel.fields[x][y].setNumber(mineField.counts[x][y], x, y);
                        } else if (mineField.visibleField[x][y].equals("/")) {
                            window.fieldPanel.fields[x][y].setExplored(x, y);
                        }
                    } catch (ArrayIndexOutOfBoundsException exception) {
                        isStopped = true;
                    }
                }
            }

        }
    }
}
