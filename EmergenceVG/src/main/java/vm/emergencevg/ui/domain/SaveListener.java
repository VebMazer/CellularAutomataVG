package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JTextField;
import vm.emergencevg.logic.GenerativeSpace;

/**
 * Save nappulan kuuntelija.
 */
public class SaveListener implements ActionListener {

    GenerativeSpace space;
    JFrame frame;
    JTextField tField;

    public SaveListener(GenerativeSpace space, JFrame frame, JTextField tField) {
        this.space = space;
        this.frame = frame;
        this.tField = tField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        space.functions.save(tField.getText());
        frame.requestFocus();
    }

}
