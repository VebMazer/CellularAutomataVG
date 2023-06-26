package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import vm.emergencevg.logic.Environment;
import vm.emergencevg.logic.Particle;

/**
 * Listener of the "particle" selection box.
 */
public class ParticleSelectListener implements ActionListener {

    JFrame frame;
    Environment environment;
    JComboBox<Particle> box;

    public ParticleSelectListener(JFrame frame, Environment environment, JComboBox<Particle> box) {
        this.frame = frame;
        this.environment = environment;
        this.box = box;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(box.getSelectedItem() != null) {
            String selected = box.getSelectedItem().toString();
            
            for (Particle particleType : environment.particles.values()) {
                
                if (selected.equals(particleType.toString())) {
                    environment.mController.pKey = particleType.key;
                }
            }
        }
        frame.requestFocus();
    }

    public void update() {
        box.addItem(environment.particles.get(environment.uFunctions.findLatestKey()));
    }

    public void empty() {
        box.removeAllItems();
    }

    public void initialize() {
        for (Particle particleType : environment.particles.values()) {
            box.addItem(particleType);
        }
    }
}
