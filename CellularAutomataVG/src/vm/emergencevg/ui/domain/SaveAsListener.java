package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import vm.emergencevg.logic.Environment;

public class SaveAsListener implements ActionListener {
    
    Environment environment;
    JFrame frame;

    public SaveAsListener(Environment environment, JFrame frame) {
        this.environment = environment;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        JFileChooser fileChooser = new JFileChooser();
        
        //fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        
        int returnValue = fileChooser.showSaveDialog(null);
        
        if (returnValue == JFileChooser.APPROVE_OPTION) {

            environment.functions.save(
                fileChooser.getCurrentDirectory().toString()
                + "/" + fileChooser.getSelectedFile().getName()
            );
        }
        
        frame.requestFocus();
    }
}
