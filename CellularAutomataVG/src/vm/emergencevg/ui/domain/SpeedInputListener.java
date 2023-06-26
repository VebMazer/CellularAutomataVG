package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JTextField;
import vm.emergencevg.logic.Environment;
import vm.emergencevg.ui.Updatable;

/**
 * "speed" text field listener.
 */
public class SpeedInputListener implements ActionListener, Updatable {

    JFrame frame;
    Environment environment;
    JTextField textField;

    public SpeedInputListener(JFrame frame, Environment environment, JTextField textField) {
        this.frame = frame;
        this.environment = environment;
        this.textField = textField;
        textField.setText("" + environment.speedModifier);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        environment.functions.setSpeedCommand(textField.getText());
        frame.requestFocus();
    }

    @Override
    public void update() {
        textField.setText("" + environment.speedModifier);
    }

}
