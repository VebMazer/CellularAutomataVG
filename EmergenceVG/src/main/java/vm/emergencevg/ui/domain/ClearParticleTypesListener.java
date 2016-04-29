package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import vm.emergencevg.logic.GenerativeSpace;

/**
 * ClearParticleTypes nappulan kuuntelija.
 */
public class ClearParticleTypesListener implements ActionListener {

    GenerativeSpace space;
    JFrame frame;
    ParticleTypeSelectListener lsListener;

    public ClearParticleTypesListener(GenerativeSpace space, JFrame frame, ParticleTypeSelectListener lsListener) {
        this.space = space;
        this.frame = frame;
        this.lsListener = lsListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        space.functions.clearCommand();
        space.functions.clearParticleTypes();
        lsListener.empty();
        frame.requestFocus();
    }
}
