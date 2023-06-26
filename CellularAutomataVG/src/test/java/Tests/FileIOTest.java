
package Tests;

import javax.swing.SwingUtilities;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import vm.emergencevg.logic.FileIO;
import vm.emergencevg.logic.Environment;
import vm.emergencevg.main.Presets;
import vm.emergencevg.ui.GUI;

/**
 *
 * @author xenron
 */
public class FileIOTest {
    Environment environment;
    FileIO fileio;
    
    public FileIOTest() {
        environment = new Environment(100, 100);
        fileio = environment.functions.fileIo;
        Presets presets = new Presets(environment);
        presets.initialTestSetup();
        environment.uFunctions.unitePresetLists();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSavingAndLoading() {
        environment.functions.clearCommand();
        environment.uFunctions.uniteCommandMaps();
        environment.functions.save("testsave.evg");
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
        
        environment.functions.loadPresentation("testsave.evg");
        boolean test1 = environment.particleTypes.get(3).name.equals("remenant");
        boolean test2 = environment.coReRunner.commands.get(0).get(0).equals("clear");
         
        assertEquals(true, test1 && test2);
     }
}
