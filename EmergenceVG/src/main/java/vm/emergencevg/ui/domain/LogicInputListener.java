package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JTextField;
import vm.emergencevg.logic.ControlFunctions;

public class LogicInputListener implements ActionListener {

    JFrame frame;
    ControlFunctions functions;
    LogicSelectListener listener;
    JTextField tField;

    public LogicInputListener(JFrame frame, ControlFunctions functions, LogicSelectListener listener, JTextField tField) {
        this.frame = frame;
        this.functions = functions;
        this.tField = tField;
        this.listener = listener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        functions.processStringToParticleType(tField.getText());
        listener.update();
        frame.requestFocus();
    }

}
