package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import vm.emergencevg.logic.ControlFunctions;

public class PlaySwitchListener implements ActionListener {

    JButton button;
    JFrame frame;
    ControlFunctions functions;

    public PlaySwitchListener(JButton button, JFrame frame, ControlFunctions functions) {
        this.button = button;
        this.frame = frame;
        this.functions = functions;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        functions.playSwitch();
        button.setText(functions.space.running ? "Stop" : "Play");
        frame.requestFocus();
    }
    
}
