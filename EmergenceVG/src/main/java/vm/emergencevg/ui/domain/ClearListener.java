package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import vm.emergencevg.logic.ControlFunctions;

/**
 * Clear nappulan kuuntelija.
 */
public class ClearListener implements ActionListener {

    JFrame frame;
    ControlFunctions functions;

    public ClearListener(JFrame frame, ControlFunctions functions) {
        this.frame = frame;
        this.functions = functions;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        functions.clear();
        frame.requestFocus();
    }

}
