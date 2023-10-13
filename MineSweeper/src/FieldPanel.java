import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class FieldPanel extends JPanel {
    Field[][] fields;

    public FieldPanel() {
        super();
//        setSize(1000, 50);
    }

    void initFields() {
        fields = new Field[MineField.height][MineField.length];
        setLayout(new GridLayout(MineField.height, MineField.length, 1, 1));
        for (int y = 0; y < fields.length; y++) {
            for (int x = 0; x < fields[y].length; x++) {
                fields[y][x] = new Field();
                fields[y][x].initField(x, y);
                this.add(fields[y][x]);
            }
        }
    }

    void showMines(MineField mineField) throws IOException {
        for (int x = 0; x < fields.length; x++) {
            for (int y = 0; y < fields[x].length; y++) {
                fields[x][y].showMine(mineField.field[x][y] == 1);
            }
        }
    }

}
