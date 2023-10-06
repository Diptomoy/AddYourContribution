import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class ActionListener extends Thread {
    GameWindow window;
    MineField mineField;
    GameController controller;
    static volatile boolean isFirstExploration = true;
    Difficulties level;
    boolean isStopped = false;

    public ActionListener(GameWindow window, MineField mineField, GameController controller, Difficulties level) {
        this.window = window;
        this.mineField = mineField;
        this.controller = controller;
        this.level = level;
    }

    @Override
    public void run() {
        if (!isStopped) {
            for (int y = 0; y < level.height; y++) {
                for (int x = 0; x < level.length; x++) {
                    int finalX = x;
                    int finalY = y;
                    window.fieldPanel.fields[y][x].addMouseListener(new MouseAdapter() {
                        @SuppressWarnings("SuspiciousNameCombination")
                        @Override
                        public void mousePressed(MouseEvent e) {
                            if (e.getButton() == MouseEvent.BUTTON3) {
                                mineField.mine(finalY, finalX);
                                if (mineField.visibleField[finalY][finalX].equals("*")) {
                                    try {
                                        window.fieldPanel.fields[finalY][finalX].setFlag();
                                    } catch (IOException exception) {
                                        exception.printStackTrace();
                                    }
                                } else if(mineField.visibleField[finalY][finalX].equals(".")){
                                    window.fieldPanel.fields[finalY][finalX].removeFlag();
                                }
                            } else if (e.getButton() == MouseEvent.BUTTON1) {
                                GameController.isSteppedOnMine = mineField.explore(finalY, finalX, isFirstExploration);
                                if (isFirstExploration) {
                                    isFirstExploration = false;
                                }
                            }
                        }
                    });
                }
            }

            window.difficultyLevel.addActionListener(e -> {
                JComboBox comboBox = (JComboBox) e.getSource();
                String selected = String.valueOf(comboBox.getSelectedItem());
//                window.difficultyLevel.setSelectedItem(comboBox.getSelectedIndex());

//            System.out.println(selected);
                Difficulties level = switch (selected) {
                    case "Easy" -> Difficulties.EASY;
                    case "Medium" -> Difficulties.MEDIUM;
                    case "Hard" -> Difficulties.HARD;
                    default -> throw new IllegalStateException("Unexpected value: " + selected);
                };
                try {
                    isFirstExploration = true;
                    window.dispose();
                    isStopped = true;
                    Main.play(level);
//                    currentThread().stop();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            });
        }
    }
}
