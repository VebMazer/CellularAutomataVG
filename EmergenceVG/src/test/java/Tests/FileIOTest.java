
package Tests;

import javax.swing.SwingUtilities;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import vm.emergencevg.logic.FileIO;
import vm.emergencevg.logic.GenerativeSpace;
import vm.emergencevg.main.Presets;
import vm.emergencevg.ui.GUI;

/**
 *
 * @author xenron
 */
public class FileIOTest {
    GenerativeSpace space;
    FileIO fileio;
    
    public FileIOTest() {
        space = new GenerativeSpace(100, 100);
        fileio = space.functions.fileIo;
        Presets presets = new Presets(space);
        presets.initialTestSetup();
        space.uFunctions.unitePresetLists();
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
        space.functions.clearCommand();
        space.uFunctions.uniteCommandMaps();
        space.functions.save("testsave.evg");
        GUI ui = new GUI(space, 5);
        SwingUtilities.invokeLater(ui);

        while (ui.drawboard == null || ui.itTracker == null) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                System.out.println("The drawboard has not been created yet.");
            }
        }
        space.coReRunner.setIterationDisplayer(ui.itTracker);
        space.functions.setScaleUpdater(ui.scaleUpdater);
        space.setUiDrawBoard(ui.drawboard);
        
        space.functions.loadPresentation("testsave.evg");
        boolean test1 = space.particleTypes.get(3).name.equals("remenant");
        boolean test2 = space.coReRunner.commands.get(0).get(0).equals("clear");
         
        assertEquals(true, test1 && test2);
     }
}
