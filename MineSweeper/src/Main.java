import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        play(Difficulties.MEDIUM);
    }

    static void play(Difficulties level) throws IOException {
        MineField mineField = new MineField(level);
        GameWindow gameWindow = new GameWindow(level);
        gameWindow.flagLabel.setText(String.valueOf(level.numOfFlags));
        GameController controller = new GameController(gameWindow, mineField);
        ActionListener listener = new ActionListener(gameWindow, mineField, controller, level);
        listener.start();
        controller.start();
    }
}
