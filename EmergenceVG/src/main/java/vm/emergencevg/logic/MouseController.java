package vm.emergencevg.logic;

import java.util.ArrayList;

/**
 * Kontrolleri, jonka kautta hiireltä saatu data välitetään taustaloopin
 * logiikka-avaruuteen.
 */
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

    /**
     * Päivittä edeltäneen ja tämänhetkisen sijainnin.
     */
    public void updateSpot(int xx, int yy) {
        this.lastX = this.x;
        this.lastY = this.y;
        this.x = xx;
        this.y = yy;
        addSpot();
    }

    /**
     * Lisää koordinaatin valittujen koordinaattien listaan jos koordinaatti ei
     * ole sama, kuin sitä edeltänyt koordinaatti. (Näin vältytään
     * duplikaateilta listassa.)
     */
    public void addSpot() {
        if (this.x != this.lastX || this.y != this.lastY) {
            spotsPressed.add(new int[]{x, y});
        }
    }

    /**
     * Lisää partikkelit listassa listaan kerättyihin koordinaatteihin. (Kun
     * käyttäjä päästää hiiren painikkeesta irti.)
     */
    public void cast() {
        if(!space.particleTypes.containsKey(pKey)) pKey = 0;
        for (int[] spot : spotsPressed) {
            space.functions.placeParticle(pKey, spot[0], spot[1]);
            addPlacementCommand(pKey, spot[0], spot[1]);
        }
        spotsPressed = new ArrayList<int[]>();
    }

    public void addPlacementCommand(int key, int x, int y) {
        space.uFunctions.addCommand("(" + key + "," + x + "," + y + ")");
    }
}
