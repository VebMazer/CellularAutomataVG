package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import vm.emergencevg.logic.GenerativeSpace;

public class ImportParticleTypesListener implements ActionListener {
    
    GenerativeSpace space;
    JFrame frame;
    ParticleTypeSelectListener particleTypeSelectListener;

    public ImportParticleTypesListener(
        GenerativeSpace space, JFrame frame,
        ParticleTypeSelectListener particleTypeSelectListener
    ) {
        this.space = space;
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
            
            space.functions.addPresets(
                fileChooser.getCurrentDirectory().toString()
                + "/" + fileChooser.getSelectedFile().getName()
            );

            particleTypeSelectListener.initialize();
        }
        
        frame.requestFocus();
    }
}
