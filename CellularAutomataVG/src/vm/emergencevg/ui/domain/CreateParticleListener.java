package vm.emergencevg.ui.domain;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import vm.emergencevg.logic.GenerativeSpace;

/**
 * Listener for the create particle option in the particle menu.
 */
public class CreateParticleListener implements ActionListener {

    JFrame frame;
    GenerativeSpace space;
    ParticleTypeSelectListener particleTypeListener;
    // ParticleTypeSelectListener listener;
    // JTextField tField;
    // ColorListener cListener;
    // FormListener fListener;

    public CreateParticleListener(
        JFrame frame,
        GenerativeSpace space,
        ParticleTypeSelectListener particleTypeListener
        // ParticleTypeSelectListener listener,  ColorListener cListener,
        // FormListener               fListener, JTextField    tField
    ) {
        this.frame = frame;
        this.space = space;
        this.particleTypeListener = particleTypeListener;
        // this.tField = tField;
        // this.listener = listener;
        // this.cListener = cListener;
        // this.fListener = fListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        requestParticleParameters();

        
    }

    private void requestParticleParameters() {
        String[] colors = {
            "Red", "Green", "Blue", "Cyan", "Magenta", "Yellow",
            "Orange", "White", "Pink", "Gray"
        };
        String[] shapes = { "3dRectangle", "Circle1", "Circle2", "Arc", "BigRectangle" };
        
        JComboBox<String> colorSelection = new JComboBox<>(colors);
        JComboBox<String> shapeSelection  = new JComboBox<>(shapes);

        JTextField nameField     = new JTextField();
        JTextField amountsForNew = new JTextField();
        JTextField amountsToLive = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));

        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Neighbor counts to create a new particle (numbers from 1-8):"));
        panel.add(amountsForNew);

        panel.add(new JLabel("Neighbor counts required for survival (numbers from 0-8):"));
        panel.add(amountsToLive);

        panel.add(new JLabel("Color:"));
        panel.add(colorSelection);

        panel.add(new JLabel("Shape:"));
        panel.add(shapeSelection);

        int result = JOptionPane.showConfirmDialog(null, panel, "Create Particle",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {

            processInputAndCreateParticle(
                nameField.getText(),     amountsForNew.getText(),
                amountsToLive.getText(), colorSelection.getSelectedIndex(),
                shapeSelection.getSelectedIndex()
            );

        }
    }

    private void processInputAndCreateParticle(
        String name, String amountsForNew,
        String amountsToLive, int color, int shape)
    {
        amountsForNew = amountsForNew.replaceAll("[^1-8]", "");
        amountsToLive = amountsToLive.replaceAll("[^0-8]", "");

        HashSet<Integer> amountsForNewSet = new HashSet<Integer>();
        HashSet<Integer> amountsToLiveSet = new HashSet<Integer>();
        
        // Convert string to a set of integers.
        for (int i = 0; i < amountsForNew.length(); i++) {
            amountsForNewSet.add( amountsForNew.charAt(i) - '0' );
        }

        // Convert string to a set of integers.
        for (int i = 0; i < amountsToLive.length(); i++) {
            amountsToLiveSet.add( amountsToLive.charAt(i) - '0' );
        }

        System.out.println(amountsForNewSet);
        System.out.println(amountsToLiveSet);
        
        createParticle(
            name,
            new ArrayList<Integer>(amountsForNewSet),
            new ArrayList<Integer>(amountsToLiveSet),
            color + 1,
            shape + 1
        );
    }

    public void createParticle(
        String name,
        ArrayList<Integer> amountsForNew,
        ArrayList<Integer> amountsToLive,
        int color,
        int shape
    ) {
        ArrayList<Integer> displayAttributes = new ArrayList<Integer>();
        displayAttributes.add(color);
        displayAttributes.add(shape);
        
        space.functions.processVariablesToParticleType(
            name, amountsForNew, amountsToLive, displayAttributes
        );
        
        particleTypeListener.update();
        frame.requestFocus();
    }

}
