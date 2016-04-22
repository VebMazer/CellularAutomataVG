package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JTextField;
import vm.emergencevg.logic.GenerativeSpace;

/**
 * LoadParticleTypes nappulan kuuntelija.
 */
public class LoadParticleTypesListener implements ActionListener {

    GenerativeSpace space;
    JFrame frame;
    JTextField tField;
    LogicSelectListener lsListener;

    public LoadParticleTypesListener(GenerativeSpace space, JFrame frame, JTextField tField, LogicSelectListener lsListener) {
        this.space = space;
        this.frame = frame;
        this.tField = tField;
        this.lsListener = lsListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        space.functions.addPresets(tField.getText());
        lsListener.update();
        frame.requestFocus();
    }

}
