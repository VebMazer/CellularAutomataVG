package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTextField;
import vm.emergencevg.domain.ParticleType;
import vm.emergencevg.logic.GenerativeSpace;

/**
 * Logiikka syötelaatikon kuuntelija.
 */
public class ParticleTypeInputListener implements ActionListener {

    JFrame frame;
    GenerativeSpace space;
    ParticleTypeSelectListener listener;
    JTextField tField;
    ColorListener cListener;
    FormListener fListener;

    public ParticleTypeInputListener(JFrame frame, GenerativeSpace space, ParticleTypeSelectListener listener, ColorListener cListener, FormListener fListener, JTextField tField) {
        this.frame = frame;
        this.space = space;
        this.tField = tField;
        this.listener = listener;
        this.cListener = cListener;
        this.fListener = fListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<Integer> displayAttributes = new ArrayList<Integer>();
        displayAttributes.add(cListener.colorKey);
        displayAttributes.add(fListener.formKey);
        space.functions.processVariablesToParticleType(tField.getText(), displayAttributes);
        listener.update();
        frame.requestFocus();
    }

}
