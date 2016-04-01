package vm.emergencevg.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import vm.emergencevg.logic.ControlFunctions;
import vm.emergencevg.logic.GenerativeSpace;

public class GUI implements Runnable {

    public JFrame frame;
    GenerativeSpace space;
    ControlFunctions functions;
    DrawBoard drawboard;
    JPanel sidePanel;
    int sideLength;

    public GUI(GenerativeSpace space, int sidelength) {
        this.space = space;
        functions = space.functions;
        this.sideLength = sidelength;
    }

    @Override
    public void run() {
        frame = new JFrame("EmergenceVG");
        int width = (space.xlength + 1) * sideLength + 10 + 100;
        int height = (space.ylength + 1) * sideLength + 10 + 20;

        frame.setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    public void createComponents(Container container) {
        sidePanel = new JPanel(new GridBagLayout());
        sidePanel.setBackground(Color.CYAN);
        container.add(sidePanel, BorderLayout.EAST);

        drawboard = new DrawBoard(space, sideLength);
        drawboard.setBackground(Color.BLACK);
        container.add(drawboard);

        MouseListen mListener = new MouseListen(space.mController, sideLength);
        drawboard.addMouseListener(mListener);
        drawboard.addMouseMotionListener(mListener);

        createPanelComponents();
    }

    public void createPanelComponents() {
        JLabel label0 = new JLabel("Describe the particle logic.");
        JLabel label1 = new JLabel("Neighbors for new:");
        JLabel label2 = new JLabel("Neighbors to live:");

        JTextField tField1 = new JTextField();
        JTextField tField2 = new JTextField();

    }

}
