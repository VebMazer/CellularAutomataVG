
package Tests;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import vm.emergencevg.logic.ControlFunctions;
import vm.emergencevg.logic.GenerativeSpace;
import vm.emergencevg.logic.UtilityFunctions;

public class UtilityFunctionsTest {
    GenerativeSpace space;
    ControlFunctions cFunctions; 
    UtilityFunctions uFunctions;
    
    public UtilityFunctionsTest() {
        space = new GenerativeSpace(100, 100);
        cFunctions = space.functions;
        uFunctions = space.uFunctions;
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

    //Testataan mostCommonKey metodin toimivuutta.
    //Metodin kuulu palauttaa yleisin kokonaisluku, joka ei ole nolla.
    @Test
    public void testMostCommonKey1() {
        int[] array = {5, 2, 0, 1, 8, 2, 0, 0};
        assertEquals(uFunctions.mostCommonKey(array), 2);
    }

    //Testataan mostCommonKey metodin toimivuutta.
    @Test
    public void testMostCommonKey2() {
        int[] array = {0, 0, 2, 5, 0, 5, 5, 0};
        assertEquals(uFunctions.mostCommonKey(array), 5);
    }
    
    @Test
    public void testMostCommonKey3() {
        int[] array = {0, 0, 0, 0, 1, 0, 0, 0};
        assertEquals(uFunctions.mostCommonKey(array), 1);
    }
    
    @Test
    public void testMostCommonKey4() {
        int[] array = {0, 0, 0, 0, 0, 0, 0, 0};
        assertEquals(uFunctions.mostCommonKey(array), 0);
    }
    
    @Test
    public void testFindLatestKey() {
        ArrayList<Integer> forNew = new ArrayList<Integer>();
        forNew.add(2);
        ArrayList<Integer> toLive = new ArrayList<Integer>();
        ArrayList<Integer> displayAttributes = new ArrayList<Integer>();
        cFunctions.addParticleType("ss", forNew, toLive, displayAttributes);
        boolean firstFound = false;
        if(uFunctions.findLatestKey() == 1) firstFound = true;
        forNew = new ArrayList<Integer>();
        forNew.add(1);
        toLive = new ArrayList<Integer>();
        cFunctions.addParticleType("sss", forNew, toLive, displayAttributes);
        boolean secondFound = false;
        if(uFunctions.findLatestKey() == 2) secondFound = true;
        
        assertEquals(true, (firstFound && secondFound));
    }
    
    @Test
    public void testCheckIfNumber1() {
        assertEquals(true, uFunctions.checkIfNumber('5'));
    }
    
    @Test
    public void testCheckIfNumber2() {
        assertEquals(false, uFunctions.checkIfNumber('y'));
    }
    
    @Test
    public void testParseNumber() {
        assertEquals(25, uFunctions.parseNumber("25 12", 0));
    }
    
    @Test
    public void testInitializeCommandArrayList() {
        space.coReRunner.iterations = 1;
        uFunctions.initializeCommandArrayList();
        boolean test = space.coReRunner.commandsToBeAdded.containsKey(1);
        space.coReRunner.iterations = 0;
        assertEquals(true, test);
    }
    
    @Test
    public void testParseNextNumber() {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        int index = uFunctions.parseNextNumber(0, "fg we 231, we2", numbers);
        int number = numbers.get(0);
        assertEquals(true, 9 == index && 231 == number);
    }
    
    @Test
    public void testCommandAddingAndIntegration() {
        space.coReRunner.iterations = 2;
        uFunctions.addCommand("clear");
        uFunctions.uniteCommandMaps();
        boolean test = space.coReRunner.commands.get(2).get(0).equals("clear");
        space.coReRunner.iterations = 0;
        assertEquals(true, test);
    }
    
    @Test
    public void testPresetAddingAndIntegration() {
        uFunctions.addPreset("l(life, 3, 2 3,,1 1)");
        uFunctions.unitePresetLists();
        boolean test = space.coReRunner.presets.get(0).equals("l(life, 3, 2 3,,1 1)");
        space.coReRunner.reInitializePresets();
        assertEquals(true, test);
    }
}
