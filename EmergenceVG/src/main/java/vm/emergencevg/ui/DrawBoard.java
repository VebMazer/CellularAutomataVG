package vm.emergencevg.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
import vm.emergencevg.logic.GenerativeSpace;
import vm.emergencevg.logic.MouseController;

/**
 * Ohjelman logiikka-avaruutta kuvaava piirtoalusta.
 */
public class DrawBoard extends JPanel implements Updatable {

    GenerativeSpace space;
    MouseController mController;
    GUI ui;
    int sideLength;

    public DrawBoard(GenerativeSpace space, GUI ui) {
        this.space = space;
        this.ui = ui;
        this.sideLength = ui.sideLength;
        this.mController = space.mController;

    }

    /**
     * Piirtää esityksen logiikka avaruudesta, ja siitä mitä hiiri on tekemässä.
     */
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
                    draw(g, space.field[i][j], i, j);
                }
            }

        }
    }
    
    /**
     * Määrittää objektin piirtotyylin.
     */
    public void draw(Graphics g, int key, int x, int y) {
        ArrayList<Integer> keys = space.particleTypes.get(key).displayAttributes;
        setColor(g, keys.get(0));           //color key is at index 0
        drawForm(g, keys.get(1), x, y);     //form key is at index 1
    }
    
    /**
     * Määrittää määrittää piirron värin.
     */
    public void setColor(Graphics g, int colorKey) {
        if (colorKey == 1) {
            g.setColor(Color.RED);
        } else if (colorKey == 2) {
            g.setColor(Color.GREEN);
        } else if (colorKey == 3) {
            g.setColor(Color.BLUE);
        } else if (colorKey == 4) {
            g.setColor(Color.CYAN);
        } else if (colorKey == 5) {
            g.setColor(Color.MAGENTA);
        } else if (colorKey == 6) {
            g.setColor(Color.YELLOW);
        } else if (colorKey == 7) {
            g.setColor(Color.ORANGE);
        } else if (colorKey == 8) {
            g.setColor(Color.WHITE);
        } else if (colorKey == 9) {
            g.setColor(Color.PINK);
        } else if (colorKey == 10) {
            g.setColor(Color.gray);
        } else {
            g.setColor(Color.white);
            //g.setColor(new Color(101, 21, 121));
        }
    }

    /**
     * piirtää määritellyn objektin.
     */
    public void drawForm(Graphics g, int formKey, int x, int y) {
        if (formKey == 1) {
            g.fill3DRect(x * sideLength, y * sideLength, sideLength, sideLength, true);
        } else if (formKey == 2) {
            g.fillOval(x * sideLength, y * sideLength, sideLength, sideLength);
        } else if (formKey == 3) {
            g.fillArc(x * sideLength, y * sideLength, sideLength, sideLength, 30, 30);
        } else if (formKey == 4) {
            g.fill3DRect(x * sideLength, y * sideLength, sideLength * 2, sideLength * 2, true);
        } else if (formKey == 5) {
            if(sideLength > 1) {
                g.fill3DRect(x * sideLength, y * sideLength, sideLength-1 , sideLength-1, true);
            } else {
                g.fill3DRect(x * sideLength, y * sideLength, sideLength , sideLength, true);
            }
        } else {
            g.fillRect(x * sideLength, y * sideLength, sideLength, sideLength);
        }
    }

    /**
     * Päivittää piirtoalustan.
     */
    @Override
    public synchronized void update() {
        sideLength = ui.sideLength;
        repaint();
    }
}
