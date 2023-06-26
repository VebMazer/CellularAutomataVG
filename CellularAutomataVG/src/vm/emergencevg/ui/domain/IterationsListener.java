package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JTextField;
import vm.emergencevg.logic.Environment;
import vm.emergencevg.ui.Updatable;

/**
 * Listens to and updates the iteration text field.
 */
public class IterationsListener implements ActionListener, Updatable {

    Environment environment;
    JFrame frame;
    JTextField tField;

    public IterationsListener(Environment environment, JFrame frame, JTextField tField) {
        this.environment = environment;
        this.frame = frame;
        this.tField = tField;
        tField.setText("0");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        environment.functions.setIterations(tField.getText());
        frame.requestFocus();
    }

    @Override
    public void update() {
        tField.setText("" + environment.coReRunner.iterations);
    }

}
