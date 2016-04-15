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
    int scale;

    public MouseListen(MouseController controller, int scale) {
        this.controller = controller;
        this.scale = scale;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        controller.updateSpot(e.getX() / scale, e.getY() / scale);
    }

    public void mouseDragged(MouseEvent e) {
        controller.updateSpot(e.getX() / scale, e.getY() / scale);
    }

    public void mouseReleased(MouseEvent e) {
        controller.cast();
    }
}
