package vm.emergencevg.main;

import javax.swing.SwingUtilities;
import vm.emergencevg.logic.Environment;
import vm.emergencevg.ui.GUI;

/**
 * Ohjelman pääfunktio sisältävä luokka, joka määrittää muutamia alkuasetuksia
 * ohjelman taustaloopille ja käyttöliittymälle, minkä jälkeen se käynnistää
 * taustaloopin.
 */
public class Main {

    public static void main(String[] args) {
        Environment environment = new Environment(250, 130);
        Presets presets = new Presets(environment);
        presets.initialTestSetup();
        
        GUI ui = new GUI(environment, 5);
        SwingUtilities.invokeLater(ui);

        while (ui.drawboard == null || ui.itTracker == null) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                System.out.println("The drawboard has not been created yet.");
            }
        }

        environment.coReRunner.setIterationDisplayer(ui.itTracker);
        environment.functions.setScaleUpdater(ui.scaleUpdater);
        environment.setUiDrawBoard(ui.drawboard);
        environment.run();
    }

}
