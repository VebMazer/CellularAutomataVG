package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;

/**
 * Muoto valinta laatikon kuuntelija.
 */
public class FormListener implements ActionListener {

    JFrame frame;
    JComboBox<String> box;
    public int formKey;

    public FormListener(JFrame frame, JComboBox<String> box) {
        formKey = 1;
        this.box = box;
        this.frame = frame;
        box.addItem("3dRectangle");
        box.addItem("Circle");
        box.addItem("Arc");
        box.addItem("BigRectangle");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (box.getSelectedItem().equals("3dRectangle")) {
            formKey = 1;
        } else if (box.getSelectedItem().equals("Circle")) {
            formKey = 2;
        } else if (box.getSelectedItem().equals("Arc")) {
            formKey = 3;
        } else if (box.getSelectedItem().equals("BigRectangle")) {
            formKey = 4;
        }
        frame.requestFocus();
    }

}
