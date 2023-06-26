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

    Environment environment;

    /**
     * Konstruktori.
     *
     * @param environment Logiikka-avaruus ja taustalooppi.
     */
    public MouseController(Environment environment) {
        this.environment = environment;
        spotsPressed = new ArrayList<int[]>();
        this.x = 0;
        this.y = 0;
        this.lastX = -10;
        this.lastY = -10;
        this.pKey = 1;
    }

    /**
     * Päivittä edeltäneen ja tämänhetkisen sijainnin.
     *
     * @param xx koordinaatin x arvo.
     * @param yy koordinaatin y arvo.
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
     * Lisää partikkelit listaan kerättyihin koordinaatteihin(Kun käyttäjä
     * päästää hiiren painikkeesta irti.).
     */
    public void cast() {
        if (!environment.particles.containsKey(pKey)) {
            pKey = 0;
        }
        for (int[] spot : spotsPressed) {
            environment.functions.placeParticle(pKey, spot[0], spot[1]);
            addPlacementCommand(pKey, spot[0], spot[1]);
        }
        spotsPressed = new ArrayList<int[]>();
    }

    /**
     * Lisää komento mappiin partikkelin sijoitus komennon.
     *
     * @param key Sijoitettavan partikkelin avain.
     * @param x Sijoitettavan partikkelin koordinaatin x akselin arvo.
     * @param y Sijoitettavan partikkelin koordinaatin y akselin arvo
     */
    public void addPlacementCommand(int key, int x, int y) {
        environment.uFunctions.addCommand("(" + key + "," + x + "," + y + ")");
    }
}
