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
        colorKey = 1;
        this.box = box;
        this.frame = frame;
        box.addItem("Red");
        box.addItem("Green");
        box.addItem("Blue");
        box.addItem("Cyan");
        box.addItem("Magenta");
        box.addItem("Yellow");
        box.addItem("Orange");
        box.addItem("White");
        box.addItem("Pink");
        box.addItem("Gray");
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
        } else if (box.getSelectedItem().equals("Orange")) {
            colorKey = 7;
        } else if (box.getSelectedItem().equals("White")) {
            colorKey = 8;
        } else if (box.getSelectedItem().equals("Pink")) {
            colorKey = 9;
        } else if (box.getSelectedItem().equals("Gray")) {
            colorKey = 10;
        }
        frame.requestFocus();
    }

}
