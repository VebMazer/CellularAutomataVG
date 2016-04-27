
package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import vm.emergencevg.ui.GUI;

public class SideLengthListener implements ActionListener{

    GUI ui;
    JTextField tField;

    public SideLengthListener(GUI ui, JTextField tField) {
        this.ui = ui;
        this.tField = tField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            ui.sideLength = Integer.parseInt(tField.getText());
        } catch(Exception ex) {
            System.out.println("Bad input!");
        }
        ui.frame.requestFocus();
    }
    
}
