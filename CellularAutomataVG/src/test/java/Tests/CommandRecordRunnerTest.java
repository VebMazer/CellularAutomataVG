
package Tests;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import vm.emergencevg.logic.CommandRecordRunner;
import vm.emergencevg.logic.Environment;
import vm.emergencevg.main.Presets;

public class CommandRecordRunnerTest {
    Environment environment;
    CommandRecordRunner coReRunner;
    
    public CommandRecordRunnerTest() {
        this.environment = new Environment(100, 100);
        coReRunner = environment.coReRunner;
        Presets presets = new Presets(environment);
        presets.initialTestSetup();
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
    public void testFindDoubleComma() {
        int index = coReRunner.findDoubleComma(0, "ig 5t ,, s t e");
        assertEquals(6, index);
    }
    
    @Test
    public void testParsePlacementStringToPlaceIt() {
        coReRunner.parsePlacementStringToPlaceIt("(1,40,40)");
        assertEquals(1, environment.field[40][40]);
    }
    
    @Test
    public void testRunningCommands() {
        environment.uFunctions.addCommand("(1,40,40)");
        environment.uFunctions.addCommand("clear");
        environment.uFunctions.addCommand("(2,41,40)");
        environment.uFunctions.addCommand("speed(5.0)");
        environment.uFunctions.uniteCommandMaps();
                coReRunner.runCommands();
        boolean cleared = false;
        boolean placed = false;
        boolean speedSet = false;
        if(environment.field[40][40] == 0) cleared = true;
        if(environment.field[41][40] == 2) placed = true;
        if(environment.speedModifier == 5.0) speedSet = true;
        
        assertEquals(true, cleared && placed && speedSet);
    }
}
