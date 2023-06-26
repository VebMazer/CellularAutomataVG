package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import vm.emergencevg.logic.Environment;

public class ImportParticlesListener implements ActionListener {
    
    Environment environment;
    JFrame frame;
    ParticleSelectListener particleTypeSelectListener;

    public ImportParticlesListener(
        Environment environment, JFrame frame,
        ParticleSelectListener particleTypeSelectListener
    ) {
        this.environment = environment;
        this.frame = frame;
        this.particleTypeSelectListener = particleTypeSelectListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        JFileChooser fileChooser = new JFileChooser();
        
        //fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        
        int returnValue = fileChooser.showOpenDialog(null);
        
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            particleTypeSelectListener.empty();
            
            environment.functions.addPresets(
                fileChooser.getCurrentDirectory().toString()
                + "/" + fileChooser.getSelectedFile().getName()
            );

            particleTypeSelectListener.initialize();
        }
        
        frame.requestFocus();
    }
}
