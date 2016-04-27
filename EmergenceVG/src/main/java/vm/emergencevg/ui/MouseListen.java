package vm.emergencevg.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import vm.emergencevg.logic.MouseController;

/**
 * Hiiri syötteen kuuntelija, joka lukee dataa piirtoalustaan suhteessa ja
 * lähettää sen sitten hiiren kontrolleri luokalle.
 */
public class MouseListen extends MouseAdapter {

    MouseController controller;
    GUI ui;

    public MouseListen(MouseController controller, GUI ui) {
        this.controller = controller;
        this.ui = ui;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        controller.updateSpot(e.getX() / ui.sideLength, e.getY() / ui.sideLength);
    }

    public void mouseDragged(MouseEvent e) {
        controller.updateSpot(e.getX() / ui.sideLength, e.getY() / ui.sideLength);
    }

    public void mouseReleased(MouseEvent e) {
        controller.cast();
    }
}
