package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;

/**
 * Väri valinta laatikon kuuntelija.
 */
public class ColorListener implements ActionListener {

    JFrame frame;
    JComboBox<String> box;
    public int colorKey;

    public ColorListener(JFrame frame, JComboBox<String> box) {
        this.box = box;
        this.frame = frame;
        box.addItem("Red");
        box.addItem("Green");
        box.addItem("Blue");
        box.addItem("Cyan");
        box.addItem("Magenta");
        box.addItem("Yellow");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (box.getSelectedItem().equals("Red")) {
            colorKey = 1;
        } else if (box.getSelectedItem().equals("Green")) {
            colorKey = 2;
        } else if (box.getSelectedItem().equals("Blue")) {
            colorKey = 3;
        } else if (box.getSelectedItem().equals("Cyan")) {
            colorKey = 4;
        } else if (box.getSelectedItem().equals("Magenta")) {
            colorKey = 5;
        } else if (box.getSelectedItem().equals("Yellow")) {
            colorKey = 6;
        }
        frame.requestFocus();
    }

}
