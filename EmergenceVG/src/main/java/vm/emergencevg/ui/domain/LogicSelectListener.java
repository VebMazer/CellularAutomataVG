package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import vm.emergencevg.domain.ParticleType;
import vm.emergencevg.logic.GenerativeSpace;

/**
 * Logiikka valinta laatikon kuuntelija.
 */
public class LogicSelectListener implements ActionListener {

    JFrame frame;
    GenerativeSpace space;
    JComboBox<ParticleType> box;

    public LogicSelectListener(JFrame frame, GenerativeSpace space, JComboBox<ParticleType> box) {
        this.frame = frame;
        this.space = space;
        this.box = box;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //update();
        if(box.getSelectedItem() != null) {
            String selected = box.getSelectedItem().toString();
            for (ParticleType particleType : space.particleTypes.values()) {
                if (selected.equals(particleType.toString())) {
                    space.mController.pKey = particleType.key;
                }
            }
        }
        frame.requestFocus();
    }

    public void update() {
//        box.removeAllItems();
//        for (ParticleType particleType : space.particleTypes.values()) {
//            box.addItem(particleType);
//        }
        box.addItem(space.particleTypes.get(space.uFunctions.findLatestKey()));
    }

    public void empty() {
        box.removeAllItems();
    }

    public void initialize() {
//        box.removeAllItems();
        for (ParticleType particleType : space.particleTypes.values()) {
            box.addItem(particleType);
        }
    }
}
