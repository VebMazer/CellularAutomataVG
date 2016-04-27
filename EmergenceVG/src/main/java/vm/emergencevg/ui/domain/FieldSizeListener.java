
package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import vm.emergencevg.logic.ControlFunctions;
import vm.emergencevg.ui.GUI;


public class FieldSizeListener implements ActionListener{

    GUI ui;
    JTextField tFieldX;
    JTextField tFieldY;
    ControlFunctions functions;

    public FieldSizeListener(GUI ui, JTextField tFieldX, JTextField tFieldY) {
        this.ui = ui;
        this.tFieldX = tFieldX;
        this.tFieldY = tFieldY;
        functions = ui.space.functions;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            functions.resetField(Integer.parseInt(tFieldX.getText()), Integer.parseInt(tFieldY.getText())); 
        } catch(Exception ex) {
            System.out.println("Bad input!");
        }
        ui.frame.requestFocus();
    }
    
}