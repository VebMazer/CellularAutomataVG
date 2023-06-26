package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import vm.emergencevg.domain.ParticleType;
import vm.emergencevg.logic.Environment;

/**
 * Listener of the "particle" selection box.
 */
public class ParticleTypeSelectListener implements ActionListener {

    JFrame frame;
    Environment environment;
    JComboBox<ParticleType> box;

    public ParticleTypeSelectListener(JFrame frame, Environment environment, JComboBox<ParticleType> box) {
        this.frame = frame;
        this.environment = environment;
        this.box = box;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(box.getSelectedItem() != null) {
            String selected = box.getSelectedItem().toString();
            
            for (ParticleType particleType : environment.particleTypes.values()) {
                
                if (selected.equals(particleType.toString())) {
                    environment.mController.pKey = particleType.key;
                }
            }
        }
        frame.requestFocus();
    }

    public void update() {
        box.addItem(environment.particleTypes.get(environment.uFunctions.findLatestKey()));
    }

    public void empty() {
        box.removeAllItems();
    }

    public void initialize() {
        for (ParticleType particleType : environment.particleTypes.values()) {
            box.addItem(particleType);
        }
    }
}
