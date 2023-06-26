package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import vm.emergencevg.logic.Environment;

/**
 * "Clear Command Record" button listener.
 */
public class ClearRecordListener implements ActionListener {

    Environment environment;
    JFrame frame;

    public ClearRecordListener(Environment environment, JFrame frame) {
        this.environment = environment;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        environment.functions.clearRecord();
        frame.requestFocus();
    }
}
