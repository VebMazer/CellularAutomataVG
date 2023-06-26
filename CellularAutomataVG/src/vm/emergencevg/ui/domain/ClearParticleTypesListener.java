package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import vm.emergencevg.logic.Environment;

/**
 * Listener for the "Delete all Particles" option in the particle menu.
 */
public class ClearParticleTypesListener implements ActionListener {

    Environment environment;
    JFrame frame;
    ParticleTypeSelectListener lsListener;

    public ClearParticleTypesListener(Environment environment, JFrame frame, ParticleTypeSelectListener lsListener) {
        this.environment = environment;
        this.frame = frame;
        this.lsListener = lsListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object[] options = {"Yes", "No"};
        int dialogResult = JOptionPane.showOptionDialog(
            frame,
            "Are you sure you want to permanently remove all particle types?",
            "Confirm Removal",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[1]);
        
        if (dialogResult == 0) removeAll();
    }

    public void removeAll() {
        environment.functions.clearCommand();
        environment.functions.clearParticleTypes();
        lsListener.empty();
        frame.requestFocus();
    }
}
