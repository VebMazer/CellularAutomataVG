package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import vm.emergencevg.domain.ParticleType;
import vm.emergencevg.logic.GenerativeSpace;

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
        String selected = box.getSelectedItem().toString();
        for (ParticleType particleType : space.particleTypes.values()) {
            if (selected.equals(particleType.toString())) {
                space.mController.pKey = particleType.key;
            }
        }
        frame.requestFocus();
    }

    public int findLatestKey() {
        int lastKey = 1;
        for (int key : space.particleTypes.keySet()) {
            if (key > lastKey) {
                lastKey = key;
            }
        }
        return lastKey;
    }

    public void update() {
//        box.removeAllItems();
//        for (ParticleType particleType : space.particleTypes.values()) {
//            box.addItem(particleType);
//        }
        box.addItem(space.particleTypes.get(findLatestKey()));
    }

    public void initialize() {
//        box.removeAllItems();
        for (ParticleType particleType : space.particleTypes.values()) {
            box.addItem(particleType);
        }
    }
}
