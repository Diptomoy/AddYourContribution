import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

public class Field extends JButton {
//    Point location;
    boolean isExplored;
    Color darkGreen = new Color(0, 169, 38);
    Color exploredDarkColor = new Color(198, 196, 153);
    Color exploredLightColor = new Color(194, 198, 176);

    public Field() {
        super();
        setVisible(true);
        isExplored = false;
//        setBackground(Color.GREEN);
    }

    void initField(int x, int y) {
        if (x % 2 == 0) {
            if (y % 2 == 0) {
                setBackground(Color.GREEN);
            } else {
                setBackground(darkGreen);
            }
        } else {
            if (y % 2 == 0) {
                setBackground(darkGreen);
            } else {
                setBackground(Color.GREEN);
            }
        }
    }

    void setNumber(int number, int x, int y) {
        setExplored(x, y);
        setText(String.valueOf(number));
    }

    void setExplored(int x, int y) {
        isExplored = true;
        if (x % 2 == 0) {
            if (y % 2 == 0) {
                this.setBackground(exploredLightColor);
            } else {
                this.setBackground(exploredDarkColor);
            }
        } else {
            if (y % 2 == 0) {
                this.setBackground(exploredDarkColor);
            } else {
                this.setBackground(exploredLightColor);
            }
        }
    }

    void setFlag() throws IOException {
        Image flagIcon = ImageIO.read(new FileInputStream("RedFlagIcon.png"));
        this.setIcon(new ImageIcon(flagIcon));
    }

    void showMine(boolean isMine) throws IOException {
        if (isMine) {
            Image bomb = ImageIO.read(new FileInputStream("bombIcon.jpg"));
            this.setIcon(new ImageIcon(bomb));
        } else {
            Icon thisIcon = this.getIcon();
            if (thisIcon != null) {
                boolean isFlagHere = thisIcon.equals(new ImageIcon(ImageIO.read(new FileInputStream("RedFlagIcon.png"))));
                if (isFlagHere) {
                    this.setIcon(new ImageIcon(ImageIO.read(new FileInputStream("RedXIcon.jpg"))));
                }
            }
        }
    }

    void removeFlag() {
        this.setIcon(null);
    }
}
