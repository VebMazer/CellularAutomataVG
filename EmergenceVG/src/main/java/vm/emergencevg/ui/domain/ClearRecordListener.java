package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JTextField;
import vm.emergencevg.logic.GenerativeSpace;

/**
 * ClearRecord nappulan kuuntelija.
 */
public class ClearRecordListener implements ActionListener {

    GenerativeSpace space;
    JFrame frame;

    public ClearRecordListener(GenerativeSpace space, JFrame frame) {
        this.space = space;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        space.functions.clearRecord();
        frame.requestFocus();
    }
}
