package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import vm.emergencevg.logic.GenerativeSpace;

public class SaveAsListener implements ActionListener {
    
    GenerativeSpace space;
    JFrame frame;

    public SaveAsListener(GenerativeSpace space, JFrame frame) {
        this.space = space;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        JFileChooser fileChooser = new JFileChooser();
        
        //fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        
        int returnValue = fileChooser.showSaveDialog(null);
        
        if (returnValue == JFileChooser.APPROVE_OPTION) {

            space.functions.save(
                fileChooser.getCurrentDirectory().toString()
                + "/" + fileChooser.getSelectedFile().getName()
            );
        }
        
        frame.requestFocus();
    }
}
