package vm.emergencevg.ui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import vm.emergencevg.logic.GenerativeSpace;
import vm.emergencevg.logic.MouseController;

public class DrawBoard extends JPanel implements Updatable {

    GenerativeSpace space;
    MouseController mController;
    int sideLength;

    public DrawBoard(GenerativeSpace space, int sideLength) {
        this.space = space;
        this.sideLength = sideLength;
        this.mController = space.mController;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        for (int[] coordinate : mController.spotsPressed) {
            g.fill3DRect(coordinate[0] * sideLength, coordinate[1] * sideLength, sideLength, sideLength, true);
        }
        g.setColor(Color.ORANGE);
        for (int j = 0; j < space.ylength; j++) {
            for (int i = 0; i < space.xlength; i++) {
                if (space.field[i][j] != 0) {
                    setColor(g, space.field[i][j]);
                    g.fill3DRect(i * sideLength, j * sideLength, sideLength, sideLength, true);
                }
            }

        }
    }

    public void setColor(Graphics g, int key) {
        if (key == 1) {
            g.setColor(Color.BLUE);
        } else if (key == 2) {
            g.setColor(Color.YELLOW);
        } else if (key == 3) {
            g.setColor(Color.RED);
        } else if (key == 4) {
            g.setColor(Color.MAGENTA);
        } else if (key == 5) {
            g.setColor(Color.CYAN);
        } else if (key == 6) {
            g.setColor(Color.GREEN);
        } else {
            g.setColor(Color.ORANGE);
        }
    }

    @Override
    public synchronized void update() {
        repaint();
    }
}
