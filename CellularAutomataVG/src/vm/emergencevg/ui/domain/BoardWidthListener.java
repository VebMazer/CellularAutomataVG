package vm.emergencevg.ui.domain;

import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vm.emergencevg.logic.ControlFunctions;
import vm.emergencevg.ui.GUI;
import vm.emergencevg.ui.Updatable;

public class BoardWidthListener implements ActionListener, Updatable {
    
    GUI ui;
    JTextField tField;
    ControlFunctions functions;
    
    public BoardWidthListener(GUI ui, JTextField tField, ControlFunctions functions) {
        this.ui = ui;
        this.tField = tField;
        this.functions = functions;
        tField.setText("" + ui.space.xlength);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int width = Integer.parseInt(tField.getText());
            functions.setBoardWidthCommand(width);
        } catch(Exception ex) {
            System.out.println("Bad input!");
        }
        ui.frame.requestFocus();
    }

    @Override
    public void update() {
        tField.setText("" + ui.space.xlength);
    }
}
