package vm.emergencevg.logic;

import java.util.ArrayList;

public class MouseController {

    int x;
    int y;
    int lastX;
    int lastY;
    public int pKey;
    public ArrayList<int[]> spotsPressed;

    GenerativeSpace space;

    public MouseController(GenerativeSpace space) {
        this.space = space;
        spotsPressed = new ArrayList<int[]>();
        this.x = 0;
        this.y = 0;
        this.lastX = -10;
        this.lastY = -10;
        this.pKey = 1;
    }

    public void updateSpot(int xx, int yy) {
        this.lastX = this.x;
        this.lastY = this.y;
        this.x = xx;
        this.y = yy;
        addSpot();
    }

    public void addSpot() {
        if (this.x != this.lastX || this.y != this.lastY) {
            spotsPressed.add(new int[]{x, y});
        }
    }

    public void cast() {
        for (int[] spot : spotsPressed) {
            space.placeParticle(pKey, spot[0], spot[1]);
        }
        spotsPressed = new ArrayList<int[]>();
    }
}
