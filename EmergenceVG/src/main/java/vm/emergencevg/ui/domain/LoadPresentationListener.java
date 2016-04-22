package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JTextField;
import vm.emergencevg.logic.GenerativeSpace;

/**
 * LoadPresentation nappulan kuuntelija.
 */
public class LoadPresentationListener implements ActionListener {

    GenerativeSpace space;
    JFrame frame;
    JTextField tField;
    LogicSelectListener lsListener;

    public LoadPresentationListener(GenerativeSpace space, JFrame frame, JTextField tField, LogicSelectListener lsListener) {
        this.space = space;
        this.frame = frame;
        this.tField = tField;
        this.lsListener = lsListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        space.functions.loadPresentation(tField.getText());
        lsListener.update();
        frame.requestFocus();
    }

}
