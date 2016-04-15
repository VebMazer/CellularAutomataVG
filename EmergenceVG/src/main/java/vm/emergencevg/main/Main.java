package vm.emergencevg.main;

import javax.swing.SwingUtilities;
import vm.emergencevg.logic.GenerativeSpace;
import vm.emergencevg.ui.GUI;

/**
 * Ohjelman pääfunktio sisältävä luokka, joka määrittää muutamia alkuasetuksia
 * ohjelman taustaloopille ja käyttöliittymälle, minkä jälkeen se käynnistää
 * taustaloopin.
 */
public class Main {

    public static void main(String[] args) {
        GenerativeSpace space = new GenerativeSpace(100, 100);
        space.functions.initialTestSetup();
        space.functions.start();
        GUI ui = new GUI(space, 10);
        SwingUtilities.invokeLater(ui);

        while (ui.drawboard == null) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                System.out.println("The drawboard has not been created yet.");
            }
        }

        space.setUiDrawBoard(ui.drawboard);
        space.run();

    }

}
