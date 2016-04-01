package vm.emergencevg.ui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import vm.emergencevg.logic.GenerativeSpace;

public class DrawBoard extends JPanel implements Updateable {

    GenerativeSpace space;
    int sideLength;

    public DrawBoard(GenerativeSpace space, int sideLength) {
        this.space = space;
        this.sideLength = sideLength;

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.RED);
        for (int j = 0; j < space.ylength; j++) {
            for (int i = 0; i < space.xlength; i++) {
                if (space.field[i][j] != 0) {
                    g.fill3DRect(i * sideLength, j * sideLength, sideLength, sideLength, true);
                }
            }

        }
    }

    @Override
    public synchronized void update() {
        repaint();
    }
}
