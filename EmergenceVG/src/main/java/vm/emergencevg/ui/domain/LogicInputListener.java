package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JTextField;
import vm.emergencevg.domain.ParticleType;
import vm.emergencevg.logic.GenerativeSpace;

/**
 * Logiikka syötelaatikon kuuntelija.
 */
public class LogicInputListener implements ActionListener {

    JFrame frame;
    GenerativeSpace space;
    LogicSelectListener listener;
    JTextField tField;
    ColorListener cListener;
    FormListener fListener;

    public LogicInputListener(JFrame frame, GenerativeSpace space, LogicSelectListener listener, ColorListener cListener, FormListener fListener, JTextField tField) {
        this.frame = frame;
        this.space = space;
        this.tField = tField;
        this.listener = listener;
        this.cListener = cListener;
        this.fListener = fListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        space.functions.processStringToParticleType(tField.getText());
        ParticleType p = space.particleTypes.get(space.uFunctions.findLatestKey());
        p.displayAttributes.set(0, cListener.colorKey);
        p.displayAttributes.set(1, fListener.formKey);
        listener.update();
        frame.requestFocus();
    }

}
