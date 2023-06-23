
package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

import vm.emergencevg.logic.ControlFunctions;
import vm.emergencevg.ui.GUI;
import vm.emergencevg.ui.Updatable;

/**
 * particleScale kentän kuuntelija.
 */
public class ScaleListener implements ActionListener, Updatable {

    GUI ui;
    JTextField tField;
    ControlFunctions functions;
    
    public ScaleListener(GUI ui, JTextField tField, ControlFunctions functions) {
        this.ui = ui;
        this.tField = tField;
        this.functions = functions;
        tField.setText("" + ui.scale);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            ui.scale = Integer.parseInt(tField.getText());
            functions.setScaleCommand(ui.scale);
        } catch(Exception ex) {
            System.out.println("Bad input!");
        }
        ui.frame.requestFocus();
    }

    @Override
    public void update() {
        ui.scale = functions.scale;
    }
    
}
