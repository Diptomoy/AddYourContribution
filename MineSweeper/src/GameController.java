import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameController extends Thread {
    GameWindow window;
    MineField mineField;
    static volatile boolean isSteppedOnMine = false;

    public GameController(GameWindow window, MineField mineField) {
        this.window = window;
        this.mineField = mineField;
    }

    @Override
    public void run() {
        mineField.createField();
        mineField.setInitVisibleField();
        mineField.countNumber();
        boolean isExplored = false;
        boolean hasFoundAllMines = false;
        window.timer.start();
        while (!isExplored && !isSteppedOnMine && !hasFoundAllMines) {
            ThreadUpdater updater = new ThreadUpdater(mineField, window);
            updater.start();
            try {
                updater.join();
            } catch (InterruptedException exception) {
                System.out.println("Error");
            }
            hasFoundAllMines = mineField.foundAllMines();
            isExplored = mineField.checkIfAllExplored();

        }
        window.timer.stop();
        JFrame closeWindow = new JFrame();
        closeWindow.setLocationRelativeTo(null);
        closeWindow.setSize(350, 10);
        closeWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (isSteppedOnMine) {
            closeWindow.setBackground(Color.RED);
            try {
                window.fieldPanel.showMines(mineField);
            } catch (IOException e) {
                e.printStackTrace();
            }
            closeWindow.add(new JLabel("You have Stepped on a Mine!, Close and Try again"));
        } else {
            closeWindow.setBackground(Color.CYAN);
            closeWindow.add(new JLabel("Congratulations, You won!, Try with increased Difficulty!"));
        }
        closeWindow.setVisible(true);
//        window.dispose();
    }
}
