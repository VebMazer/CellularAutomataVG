package vm.emergencevg.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

import vm.emergencevg.logic.Environment;
import vm.emergencevg.logic.MouseController;

/**
 * Displays the CellTable.
 */
public class DrawBoard extends JPanel implements Updatable {

    Environment environment;
    MouseController mController;
    GUI ui;
    int sideLength;

    public DrawBoard(Environment environment, GUI ui) {
        this.environment = environment;
        this.ui = ui;
        this.sideLength = ui.scale;
        this.mController = environment.mController;

    }

    /**
     * Display the CellTable and what the mouse is doing.
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(Color.GRAY);
        drawBorders(g);
        
        g.setColor(Color.WHITE);
        
        for (int[] coordinate : mController.spotsPressed) {
            g.fill3DRect(coordinate[0] * sideLength, coordinate[1] * sideLength, sideLength, sideLength, true);
        }
        
        g.setColor(Color.ORANGE);
        
        for (int j = 0; j < environment.height; j++) {
            for (int i = 0; i < environment.width; i++) {
                if (environment.field[i][j] != 0) {
                    draw(g, environment.field[i][j], i, j);
                }
            }

        }
    }

    public void drawBorders(Graphics g) {
        int width  = environment.width * sideLength;
        int height = environment.height * sideLength;
        
        // Draw right side border line.
        g.drawLine(width, 0, width, height);
        
        // Draw bottom border line.
        g.drawLine(0, height, width, height);
    }
    
    /**
     * Määrittää objektin piirtotyylin.
     */
    public void draw(Graphics g, int key, int x, int y) {
        ArrayList<Integer> keys = environment.particleTypes.get(key).displayAttributes;
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
            g.setColor(Color.lightGray);
        }
    }

    /**
     * Piirtää määritellyn objektin.
     */
    public void drawForm(Graphics g, int formKey, int x, int y) {
        if (formKey == 1) {
            g.fill3DRect(x * sideLength, y * sideLength, sideLength, sideLength, true);
        } else if (formKey == 2) {
            g.drawOval(x*sideLength, y*sideLength, sideLength, sideLength);
        } else if (formKey == 3) {
            g.fillOval(x*sideLength, y*sideLength, sideLength, sideLength);
        } else if (formKey == 4) {
            g.fillArc(x * sideLength, y * sideLength, sideLength, sideLength, 30, 30);
        } else if (formKey == 5) {
            g.fill3DRect(x * sideLength, y * sideLength, sideLength * 2, sideLength * 2, true);
        } else {
            g.fillRect(x * sideLength, y * sideLength, sideLength, sideLength);
        }
    }
    
    /**
     * Päivittää piirtoalustan.
     */
    @Override
    public synchronized void update() {
        sideLength = ui.scale;
        repaint();
    }
}
