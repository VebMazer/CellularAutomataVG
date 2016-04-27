package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JTextField;
import vm.emergencevg.logic.GenerativeSpace;

/**
 * Speed kentän kuuntelija.
 */
public class SpeedInputListener implements ActionListener {

    JFrame frame;
    GenerativeSpace space;
    JTextField tField;

    public SpeedInputListener(JFrame frame, GenerativeSpace space, JTextField tField) {
        this.frame = frame;
        this.space = space;
        this.tField = tField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        space.functions.setSpeedCommand(tField.getText());
        frame.requestFocus();
    }

}
