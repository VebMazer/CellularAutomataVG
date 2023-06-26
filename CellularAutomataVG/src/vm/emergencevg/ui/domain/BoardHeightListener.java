package vm.emergencevg.ui.domain;

import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vm.emergencevg.logic.ControlFunctions;
import vm.emergencevg.ui.GUI;
import vm.emergencevg.ui.Updatable;

public class BoardHeightListener implements ActionListener, Updatable {
    
    GUI ui;
    JTextField tField;
    ControlFunctions functions;
    
    public BoardHeightListener(GUI ui, JTextField tField, ControlFunctions functions) {
        this.ui = ui;
        this.tField = tField;
        this.functions = functions;
        tField.setText("" + ui.environment.height);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int height = Integer.parseInt(tField.getText());
            functions.setBoardHeightCommand(height);
        } catch(Exception ex) {
            System.out.println("Bad input!");
        }
        ui.frame.requestFocus();
    }

    @Override
    public void update() {
        tField.setText("" + ui.environment.height);
    }
}
