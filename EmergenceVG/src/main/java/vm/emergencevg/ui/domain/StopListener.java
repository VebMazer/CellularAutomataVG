package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import vm.emergencevg.logic.ControlFunctions;

public class StopListener implements ActionListener {

    JFrame frame;
    ControlFunctions functions;

    public StopListener(JFrame frame, ControlFunctions functions) {
        this.frame = frame;
        this.functions = functions;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        functions.stop();
        frame.requestFocus();
    }

}
