package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JTextField;
import vm.emergencevg.logic.GenerativeSpace;
import vm.emergencevg.ui.Updatable;

/**
 * Iterations kentän kuuntelija ja päivittäjä.
 */
public class IterationsListener implements ActionListener, Updatable {

    GenerativeSpace space;
    JFrame frame;
    JTextField tField;

    public IterationsListener(GenerativeSpace space, JFrame frame, JTextField tField) {
        this.space = space;
        this.frame = frame;
        this.tField = tField;
        tField.setText("0");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        space.functions.setIterations(tField.getText());
        frame.requestFocus();
    }

    @Override
    public void update() {
        tField.setText("" + space.coReRunner.iterations);
    }

}
