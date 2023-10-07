import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

public class GameWindow extends JFrame {
    JComboBox<String> difficultyLevel;
    JLabel flagLabel;
    JLabel stopWatchLabel;
    JPanel topPanel;
    FieldPanel fieldPanel;
    private int time = 0;
    Timer timer;

    public GameWindow(Difficulties level) throws IOException {
        super("MineSweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

//        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 5));
        add(topPanel, BorderLayout.NORTH);

        difficultyLevel = new JComboBox<>();
        difficultyLevel.addItem("Easy");
        difficultyLevel.addItem("Medium");
        difficultyLevel.addItem("Hard");
        if (level == Difficulties.EASY) {
            difficultyLevel.setSelectedIndex(0);
        } else if (level == Difficulties.MEDIUM) {
            difficultyLevel.setSelectedIndex(1);
        } else {
            difficultyLevel.setSelectedIndex(2);
        }
        topPanel.add(difficultyLevel);

        flagLabel = new JLabel();
        Image flagIcon = ImageIO.read(new FileInputStream("RedFlagIcon.png"));
        flagLabel.setIcon(new ImageIcon(flagIcon));
        flagLabel.setText("35");
        topPanel.add(flagLabel);

        stopWatchLabel = new JLabel();
        Image stopWatch = ImageIO.read(new FileInputStream("ClockIcon.png"));
        stopWatchLabel.setIcon(new ImageIcon(stopWatch));
        stopWatchLabel.setText("000");
        topPanel.add(stopWatchLabel);

        timer = new Timer(1000, e -> {
            stopWatchLabel.setText(String.format("%d", ++time));
        });

        fieldPanel = new FieldPanel();
        fieldPanel.initFields();
        add(fieldPanel);

        setVisible(true);
    }
}
